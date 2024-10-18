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

class TestThreeActivity : AppCompatActivity() {

    private var currentQuestionIndex = 0
    private var score = 0
    private lateinit var questionList: List<Question>
    private lateinit var back: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_test_three)
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

    private fun loadQuestions(): List<Question> {
        return listOf(
            // Marathi Questions
            Question(1, "प्रश्न १: 'पुस्तक' चा अर्थ काय?", listOf("A) लेखनाचा साधन", "B) वाचनाचा साधन", "C) ज्ञानाचा स्त्रोत", "D) सर्व"), 3),
            Question(2, "प्रश्न २: 'संपूर्ण' या वाक्यातील विशेषण कोणते?", listOf("A) जग", "B) आहे", "C) सर्व", "D) म्हणून"), 2),
            Question(3, "प्रश्न ३: 'नदी' च्या अलीकडील शब्दाचा अर्थ काय?", listOf("A) नदीचा प्रवाह", "B) ताजगी", "C) जलधार", "D) सर्व"), 0),
            Question(4, "प्रश्न ४: 'कविता' म्हणजे काय?", listOf("A) कथा", "B) गाणे", "C) भावना व्यक्त करणारे लेखन", "D) माहिती"), 2),
            Question(5, "प्रश्न ५: 'भाषा' म्हणजे काय?", listOf("A) संवाद साधण्याचे माध्यम", "B) सांस्कृतिक वैशिष्ट्य", "C) लोकांचे विचार", "D) सर्व"), 3),

            // Ankaganit (Mathematics) Questions
            Question(6, "प्रश्न ६: ७ + ५ = ?", listOf("A) 10", "B) 11", "C) 12", "D) 13"), 2),
            Question(7, "प्रश्न ७: १५ चा २०% म्हणजे किती?", listOf("A) 2", "B) 3", "C) 4", "D) 5"), 3),
            Question(8, "प्रश्न ८: ६ * ९ = ?", listOf("A) 54", "B) 60", "C) 64", "D) 72"), 0),
            Question(9, "प्रश्न ९: १० चा वर्ग कोणता आहे?", listOf("A) 90", "B) 100", "C) 80", "D) 70"), 1),
            Question(10, "प्रश्न १०: १/२ + १/२ = ?", listOf("A) 1", "B) 2", "C) 1/2", "D) 0"), 0),

            // Buddhimatta Chachni (Logical Reasoning) Questions
            Question(11, "प्रश्न ११: 'सर्व माणसं मर्त्य आहेत' या विधानाचा विरोधी विधान काय?", listOf("A) काही माणसं अमर आहेत", "B) सर्व माणसं अमर आहेत", "C) काही माणसं मर्त्य आहेत", "D) सर्व माणसं मृत्यूस येणार नाहीत"), 0),
            Question(12, "प्रश्न १२: 'नाही' म्हणजे कोणते?", listOf("A) असामान्य", "B) नकारात्मक", "C) सकारात्मक", "D) तटस्थ"), 1),
            Question(13, "प्रश्न १३: ८ = २x, x = ?", listOf("A) 2", "B) 4", "C) 6", "D) 8"), 1),
            Question(14, "प्रश्न १४: 'बोलणे' या क्रियापदाचा भूतकाळ कोणता?", listOf("A) बोलतो", "B) बोलेन", "C) बोललो", "D) बोलत आहे"), 2),
            Question(15, "प्रश्न १५: जर A > B असेल आणि B > C असेल, तर A > C हे सत्य आहे का?", listOf("A) हो", "B) नाही", "C) कदाचित", "D) ते सिद्ध करणे आवश्यक आहे"), 0),

            // Bhumati (Geography) Questions
            Question(16, "प्रश्न १६: भारतात कोणती राजधानी आहे?", listOf("A) मुंबई", "B) दिल्ली", "C) बंगळूर", "D) कोलकाता"), 1),
            Question(17, "प्रश्न १७: जगातील सर्वात मोठा महासागर कोणता आहे?", listOf("A) अटलांंटिक", "B) भारतीय", "C) प्रशांत", "D) आर्कटिक"), 2),
            Question(18, "प्रश्न १८: 'मायनमार' देश कोणत्या खंडात आहे?", listOf("A) आफ्रिका", "B) आशिया", "C) युरोप", "D) ऑस्ट्रेलिया"), 1),
            Question(19, "प्रश्न १९: भारतीय उपखंडामध्ये कोणते देश आहेत?", listOf("A) भारत, पाकिस्तान, बांगलादेश", "B) भारत, अमेरिका, कॅनडा", "C) भारत, चीन, जपान", "D) भारत, रशिया, इटली"), 0),
            Question(20, "प्रश्न २०: 'संपूर्ण जग' या वाक्यातील विशेषण कोणते?", listOf("A) जग", "B) संपूर्ण", "C) आहे", "D) येते"), 1),

            // Chalu Ghadamodi (Current Affairs) Questions
            Question(21, "प्रश्न २१: २०२३ मध्ये भारतात कोणत्या पक्षाचा विजय झाला?", listOf("A) भाजप", "B) काँग्रेस", "C) आम आदमी पार्टी", "D) शिवसेना"), 0),
            Question(22, "प्रश्न २२: २०२३ मध्ये 'मिस युनिव्हर्स' चा किताब कोणाला मिळाला?", listOf("A) हरनाज संधू", "B) सुष्मिता सेन", "C) प्रिया अल्वारो", "D) यापैकी नाही"), 1),
            Question(23, "प्रश्न २३: २०२३ मध्ये कोणत्या देशात 'वर्ल्ड कप' स्पर्धा झाली?", listOf("A) भारत", "B) ऑस्ट्रेलिया", "C) इंग्लंड", "D) पाकिस्तान"), 0),
            Question(24, "प्रश्न २४: २०२३ मध्ये 'ऑस्कर' पुरस्कार कोणत्या चित्रपटाला मिळाला?", listOf("A) द फादर", "B) नो टाइम टू डाई", "C) द पावर ऑफ द डॉग", "D) यापैकी नाही"), 2),
            Question(25, "प्रश्न २५: २०२३ मध्ये भारतीय क्रिकेट संघाचे कर्णधार कोण होते?", listOf("A) विराट कोहली", "B) रोहित शर्मा", "C) एम.एस. धोनी", "D) यापैकी नाही"), 1),

            // English Questions
            Question(26, "Question 26: Which of the following sentences is correct?", listOf("A) He go to school", "B) He goes to school", "C) He gone to school", "D) He going to school"), 1),
            Question(27, "Question 27: Identify the antonym of 'Difficult'", listOf("A) Hard", "B) Easy", "C) Tough", "D) Complicated"), 1),
            Question(28, "Question 28: What is the superlative form of 'good'?", listOf("A) Goodest", "B) Better", "C) Best", "D) Well"), 2),
            Question(29, "Question 29: Which word is a synonym for 'happy'?", listOf("A) Sad", "B) Joyful", "C) Angry", "D) Tired"), 1),
            Question(30, "Question 30: Choose the correct tense: \"She ______ to the store yesterday.\"", listOf("A) go", "B) goes", "C) went", "D) gone"), 2)
        )
    }

}
