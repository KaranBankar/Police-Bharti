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

class TestTwoActivity : AppCompatActivity() {

    private var currentQuestionIndex = 0
    private var score = 0
    private lateinit var questionList: List<Question>
    private lateinit var back: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_test_two)
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
            Question(1, "प्रश्न १: 'स्वातंत्र्य' या शब्दाचा अर्थ कोणता?", listOf("A) बंधन", "B) मुक्तता", "C) असुरक्षा", "D) नियंत्रण"), 1),
            Question(2, "प्रश्न २: 'कविता' हा कोणत्या साहित्य प्रकाराचा एक भाग आहे?", listOf("A) कथा", "B) निबंध", "C) नाटक", "D) गीत"), 3),
            Question(3, "प्रश्न ३: 'रामायण' या ग्रंथात मुख्य पात्र कोणते आहे?", listOf("A) सीता", "B) हनुमान", "C) राम", "D) लक्ष्मण"), 2),
            Question(4, "प्रश्न ४: 'गुणगुण' शब्दाचा अर्थ कोणता?", listOf("A) शांतता", "B) वाद्य", "C) गाणे", "D) कथा"), 0),
            Question(5, "प्रश्न ५: 'संपूर्ण जग' या वाक्यातील विशेषण कोणते?", listOf("A) जग", "B) संपूर्ण", "C) आहे", "D) येते"), 1),

            // Ankaganit Questions
            Question(6, "प्रश्न ६: ५ * ८ = ?", listOf("A) 45", "B) 48", "C) 40", "D) 50"), 1),
            Question(7, "प्रश्न ७: एका त्रिकोणाच्या पायऱ्या किती आहेत?", listOf("A) 3", "B) 4", "C) 5", "D) 6"), 0),
            Question(8, "प्रश्न ८: कोणती संख्या २९ च्या पूर्णांकाने विभाज्य आहे?", listOf("A) 58", "B) 59", "C) 60", "D) 61"), 0),
            Question(9, "प्रश्न ९: २५% म्हणजे किती?", listOf("A) 1/4", "B) 1/5", "C) 1/3", "D) 1/6"), 0),
            Question(10, "प्रश्न १०: २^३ चा मूल्य किती आहे?", listOf("A) 4", "B) 6", "C) 8", "D) 10"), 2),

            // Buddhimatta Chachni Questions
            Question(11, "प्रश्न ११: खालीलपैकी कोणता अल्गोरिदम आहे?", listOf("A) बायनरी सर्च", "B) समुच्चय सिद्धांत", "C) डेटा संरचना", "D) भिन्न समीकरण"), 0),
            Question(12, "प्रश्न १२: एक त्रिकोणात एकाच बाजूच्या दोन्ही बाजू किती असू शकतात?", listOf("A) 3", "B) 4", "C) 5", "D) 6"), 0),
            Question(13, "प्रश्न १३: 30 च्या 50% म्हणजे किती?", listOf("A) 15", "B) 20", "C) 10", "D) 25"), 0),
            Question(14, "प्रश्न १४: 8, 16, 32, ... या अनुक्रमात पुढील संख्या कोणती असेल?", listOf("A) 48", "B) 64", "C) 80", "D) 100"), 1),
            Question(15, "प्रश्न १५: 15 चा वर्गमूळ कोणता आहे?", listOf("A) 225", "B) 15", "C) 30", "D) 20"), 0),

            // Bhumati Questions
            Question(16, "प्रश्न १६: जगातील सर्वात मोठा महासागर कोणता आहे?", listOf("A) अटलांटिक", "B) प्रशांत", "C) भारतीय", "D) आर्कटिक"), 1),
            Question(17, "प्रश्न १७: भारतातील सर्वात उंच पर्वत कोणता आहे?", listOf("A) कंचनजंगा", "B) नंदादेवी", "C) एव्हरेस्ट", "D) धौलागिरी"), 2),
            Question(18, "प्रश्न १८: 'मायनमार' देश कोणत्या खंडात आहे?", listOf("A) आफ्रिका", "B) आशिया", "C) युरोप", "D) ऑस्ट्रेलिया"), 1),
            Question(19, "प्रश्न १९: 'माझी जमीन, माझा अधिकार' हा नारा कोणत्या चळवळीचा आहे?", listOf("A) स्वातंत्र्य चळवळ", "B) भूमी अधिकार चळवळ", "C) हरित क्रांती", "D) ओबीसी चळवळ"), 1),
            Question(20, "प्रश्न २०: 'गंगा नदी' कुठून वाहते?", listOf("A) हिमालय", "B) डेक्कन", "C) खैरे", "D) वायव्य"), 0),

            // Chalu Ghadamodi Questions
            Question(21, "प्रश्न २१: २०२३ च्या भारतातील लोकशाहीत कोणत्या पक्षाचा विजय झाला?", listOf("A) भाजप", "B) काँग्रेस", "C) आम आदमी पार्टी", "D) शिवसेना"), 0),
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
