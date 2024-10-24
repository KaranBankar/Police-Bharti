package com.example.policebharti

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class EnglishSubjectActivity : AppCompatActivity() {

    private var currentQuestionIndex = 0
    private var score = 0
    private lateinit var questionList: List<Question>
    private lateinit var back: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_english_subject)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        back=findViewById(R.id.back)
        val tv = findViewById<TextView>(R.id.count)
        questionList = loadQuestions() // Load or fetch questions

        displayQuestion(currentQuestionIndex)
        tv.text = "${currentQuestionIndex + 1}"

        // Move to the next question
        findViewById<Button>(R.id.btnNext).setOnClickListener {
            handleNextQuestion(tv)
        }

        // Move to the previous question
        findViewById<Button>(R.id.btnPre).setOnClickListener {
            handlePreviousQuestion(tv)
        }

        back.setOnClickListener {
            var i= Intent(this,HomeActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun handleNextQuestion(tv: TextView) {
        val selectedOptionId = findViewById<RadioGroup>(R.id.rgOptions).checkedRadioButtonId

        if (selectedOptionId != -1) {
            val isCorrect = selectedOptionId == questionList[currentQuestionIndex].correctAnswerIndex
            if (isCorrect) {
                score++ // Increase the score for correct answer
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show()
            }

            currentQuestionIndex++

            if (currentQuestionIndex < questionList.size) {
                displayQuestion(currentQuestionIndex)
                tv.text = "${currentQuestionIndex + 1}"
            } else {
                // End of quiz
                Toast.makeText(this, "Quiz finished! Your score is $score/${questionList.size}", Toast.LENGTH_SHORT).show()
                resetQuiz()
            }
        } else {
            Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handlePreviousQuestion(tv: TextView) {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--
            displayQuestion(currentQuestionIndex)
            tv.text = "${currentQuestionIndex + 1}"
        } else {
            Toast.makeText(this, "This is the first question!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayQuestion(index: Int) {
        val question = questionList[index]
        findViewById<TextView>(R.id.tvQuestion).text = question.questionText

        val radioGroup = findViewById<RadioGroup>(R.id.rgOptions)
        radioGroup.removeAllViews()

        // Dynamically add options as radio buttons
        question.options.forEachIndexed { i, option ->
            val radioButton = RadioButton(this)
            radioButton.text = option
            radioButton.id = i
            radioGroup.addView(radioButton)
        }
    }

    private fun resetQuiz() {
        currentQuestionIndex = 0
        score = 0
        findViewById<TextView>(R.id.count).text = "${currentQuestionIndex + 1}"
        displayQuestion(currentQuestionIndex)
    }

    fun loadQuestions(): List<Question> {
        return listOf(
            // English Questions 1 to 50
            Question(1, "Which of the following is an antonym of the word 'benevolent'?", listOf("A) Malevolent", "B) Compassionate", "C) Altruistic", "D) Kind"), 0),
            Question(2, "What is the passive voice of the sentence: 'She is writing a letter'?", listOf("A) A letter is being written by her", "B) A letter was being written by her", "C) A letter has been written by her", "D) A letter had been written by her"), 0),
            Question(3, "Which of the following sentences is grammatically correct?", listOf("A) He don't like apples", "B) She doesn't likes apples", "C) She doesn't like apples", "D) He doesn't likes apple"), 2),
            Question(4, "Identify the figure of speech in the sentence: 'The wind whispered through the trees.'", listOf("A) Simile", "B) Metaphor", "C) Personification", "D) Hyperbole"), 2),
            Question(5, "Choose the correct meaning of the idiom: 'Bite the bullet.'", listOf("A) To face a difficult situation bravely", "B) To run away from danger", "C) To make a rash decision", "D) To avoid responsibility"), 0),
            Question(6, "Which of the following words is a synonym for 'abundant'?", listOf("A) Scarce", "B) Plentiful", "C) Rare", "D) Limited"), 1),
            Question(7, "Which tense is used in the sentence: 'By the time you arrive, I will have finished my homework'?", listOf("A) Present Perfect", "B) Future Perfect", "C) Future Continuous", "D) Present Continuous"), 1),
            Question(8, "Identify the type of sentence: 'What a beautiful painting!'", listOf("A) Declarative", "B) Interrogative", "C) Imperative", "D) Exclamatory"), 3),
            Question(9, "Which of the following is a compound sentence?", listOf("A) She ran quickly", "B) She ran, and he walked", "C) Because it was raining, she stayed home", "D) She stayed home due to the rain"), 1),
            Question(10, "What is the correct form of the verb in this sentence: 'I suggest that he ____ to the meeting.'", listOf("A) come", "B) comes", "C) came", "D) is coming"), 0),
            Question(11, "Which part of speech is the word 'quickly' in the sentence: 'He quickly ran to the store.'?", listOf("A) Noun", "B) Adverb", "C) Adjective", "D) Verb"), 1),
            Question(12, "Which of the following is an example of a metaphor?", listOf("A) He runs as fast as a cheetah", "B) Life is a rollercoaster", "C) The stars twinkle like diamonds", "D) She is as busy as a bee"), 1),
            Question(13, "Which of the following sentences contains a dangling modifier?", listOf("A) Running down the street, the car stopped suddenly", "B) The car stopped suddenly as I was running", "C) I stopped the car suddenly while running", "D) Running down the street, I stopped the car"), 0),
            Question(14, "What is the plural form of the word 'criterion'?", listOf("A) Criterias", "B) Criterions", "C) Criteria", "D) Criteriae"), 2),
            Question(15, "Choose the sentence with the correct use of punctuation.", listOf("A) It's a nice day, isn't it", "B) Its a nice day, isn't it?", "C) It's a nice day, isn't it?", "D) Its a nice day isn't it?"), 2),
            Question(16, "Which of the following words is a homophone of 'site'?", listOf("A) Cite", "B) Sigh", "C) Sight", "D) Side"), 2),
            Question(17, "Which of the following words is the correct form of the verb in this sentence: 'He ___ the team last year.'", listOf("A) lead", "B) leads", "C) led", "D) leading"), 2),
            Question(18, "Identify the literary device used: 'Her smile was as bright as the sun.'", listOf("A) Alliteration", "B) Metaphor", "C) Simile", "D) Hyperbole"), 2),
            Question(19, "Choose the correct sentence.", listOf("A) I have went to the store", "B) I have gone to the store", "C) I had go to the store", "D) I will gone to the store"), 1),
            Question(20, "Which of the following words is a synonym for 'placate'?", listOf("A) Agitate", "B) Soothe", "C) Irritate", "D) Confuse"), 1),
            Question(21, "Which sentence uses the subjunctive mood?", listOf("A) If I were you, I would apologize", "B) I wish I am taller", "C) He runs every day", "D) They are going to the store"), 0),
            Question(22, "Which of the following is a gerund?", listOf("A) Running", "B) To run", "C) Runs", "D) Ran"), 0),
            Question(23, "What is the correct comparative form of 'good'?", listOf("A) More good", "B) Gooder", "C) Better", "D) Best"), 2),
            Question(24, "What is the object of the preposition in the sentence: 'She sat beside him.'", listOf("A) Sat", "B) Beside", "C) She", "D) Him"), 3),
            Question(25, "Which word is the correct antonym of 'obtuse'?", listOf("A) Dull", "B) Sharp", "C) Slow", "D) Blunt"), 2),
            Question(26, "What is the correct form of the verb in this sentence: 'By this time next year, they ____ married for ten years.'", listOf("A) are being", "B) will have been", "C) had been", "D) will be"), 1),
            Question(27, "Choose the sentence with the correct subject-verb agreement.", listOf("A) Each of the students are working hard", "B) Everyone have completed their tasks", "C) Neither of the answers is correct", "D) The team are winning"), 2),
            Question(28, "Which of the following sentences uses an oxymoron?", listOf("A) The silence was deafening", "B) She is as quiet as a mouse", "C) He is the black sheep of the family", "D) The stars were shining brightly"), 0),
            Question(29, "What is the correct superlative form of 'funny'?", listOf("A) More funny", "B) Funnier", "C) Funniest", "D) Most funny"), 2),
            Question(30, "Which of the following sentences is written in the past perfect tense?", listOf("A) She had finished her homework before dinner", "B) She finishes her homework", "C) She is finishing her homework", "D) She will finish her homework later"), 0),
            Question(31, "Which of the following is a prepositional phrase?", listOf("A) Running fast", "B) Over the hill", "C) Jumping up", "D) Quickly running"), 1),
            Question(32, "Choose the sentence that contains a relative clause.", listOf("A) She is the one who won the contest", "B) I like ice cream", "C) The sky is blue", "D) They run quickly"), 0),
            Question(33, "What is the literary device used in: 'I have a million things to do.'", listOf("A) Simile", "B) Metaphor", "C) Hyperbole", "D) Personification"), 2),
            Question(34, "Which of the following words is spelled correctly?", listOf("A) Receve", "B) Recieve", "C) Receive", "D) Recive"), 2),
            Question(35, "What is the base form of the verb in the sentence: 'She has spoken to him.'", listOf("A) Speak", "B) Speaking", "C) Spoke", "D) Spoken"), 0),
            Question(36, "Which of the following is an example of alliteration?", listOf("A) The big, blue balloon", "B) As brave as a lion", "C) He is a shining star", "D) Her smile was like the sun"), 0),
            Question(37, "Choose the correct form of the verb: 'If he ____ more careful, he wouldn't have made that mistake.'", listOf("A) was", "B) were", "C) is", "D) will be"), 1),
            Question(38, "What is the main clause in the sentence: 'Although it was raining, we decided to go for a walk.'", listOf("A) Although it was raining", "B) We decided to go for a walk", "C) It was raining", "D) To go for a walk"), 1),
            Question(39, "Which of the following is an example of personification?", listOf("A) The clouds danced across the sky", "B) She runs like the wind", "C) His heart was a stone", "D) The snow is white as a blanket"), 0),
            Question(40, "Which sentence is in the conditional mood?", listOf("A) If I were rich, I would travel the world", "B) She is traveling the world", "C) I wish I can travel the world", "D) She travels the world every year"), 0),
            Question(41, "What is the function of the infinitive in the sentence: 'He wants to succeed in life.'", listOf("A) Subject", "B) Direct object", "C) Adverb", "D) Adjective"), 1),
            Question(42, "Which of the following sentences contains an appositive?", listOf("A) My brother, a doctor, works at the hospital", "B) The cat ran under the bed", "C) She is the tallest in the class", "D) I like to read books"), 0),
            Question(43, "What is the antecedent of the pronoun 'they' in the sentence: 'The students said they were ready for the test.'", listOf("A) Test", "B) They", "C) Said", "D) Students"), 3),
            Question(44, "Which of the following sentences is in active voice?", listOf("A) The book was read by the child", "B) The child read the book", "C) The book is being read by the child", "D) The child will have been reading the book"), 1),
            Question(45, "Which of the following is an example of irony?", listOf("A) A fire station burns down", "B) The sun sets in the west", "C) She sings beautifully", "D) He is taller than his brother"), 0),
            Question(46, "Which sentence contains a noun clause?", listOf("A) I know that she is coming", "B) She runs quickly", "C) He is very tall", "D) The sky is blue"), 0),
            Question(47, "Which of the following is a complex sentence?", listOf("A) She likes to read and write", "B) She likes to read, but she doesn't like to write", "C) Because she likes to read, she often visits the library", "D) She likes reading, writing, and dancing"), 2),
            Question(48, "Which of the following is an example of a coordinating conjunction?", listOf("A) Because", "B) However", "C) And", "D) Although"), 2),
            Question(49, "Choose the sentence with parallel structure.", listOf("A) She likes to swim, to run, and biking", "B) She likes swimming, running, and biking", "C) She likes to swim, running, and biking", "D) She likes swimming, to run, and biking"), 1),
            Question(50, "What is the indirect object in the sentence: 'He gave his friend a gift.'", listOf("A) He", "B) Gift", "C) His friend", "D) Gave"), 2)
        )
    }


}