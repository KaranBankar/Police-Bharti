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

class TestOneActivity : AppCompatActivity() {

    private var currentQuestionIndex = 0
    private var score = 0
    private lateinit var questionList: List<Question>
    private lateinit var back:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_test_one)
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
            var i=Intent(this,HomeActivity::class.java)
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
            Question(1, "प्रश्न १: 'भारताची राजधानी दिल्ली आहे' या वाक्यातील नाव सर्वनाम कोणते?", listOf("A) दिल्ली", "B) आहे", "C) भारताची", "D) राजधानी"), 3),
            Question(2, "प्रश्न २: 'तो फार चांगला मुलगा आहे' या वाक्यातील विशेषण कोणते?", listOf("A) तो", "B) चांगला", "C) मुलगा", "D) आहे"), 1),
            Question(3,"प्रश्न ३: 'सूर्य पूर्वेला उगवतो' या वाक्यातील क्रियापद कोणते?", listOf("A) सूर्य","B) पूर्वेला","C) उगवतो","D) वाक्य नाही"),2),
            Question(4,"प्रश्न ४: 'सागर जसा शांत असतो, तसा तो आहे' या वाक्यातील तुलना कशाशी केली आहे?", listOf("A) वारा","B) आकाश","C) सागर","D) नदी"),2),
            Question(5,"प्रश्न ५: 'तिच्या' या शब्दाचा प्रकार कोणता आहे?", listOf("A) सर्वनाम","B) नाम","C) क्रियापद","D) विशेषण"),0),
            Question(6,"प्रश्न ६: एका विशिष्ट सांकेतिक भाषेत 'CAT' शब्द 'DBU' असा लिहिला जातो. तर 'DOG' कसा लिहिला जाईल?", listOf("A) EPH","B) EPHI","C) DPH","D) EOG"),0),
            Question(7,"प्रश्न ७: समजा, क्रमात असलेले 2, 4, 8, 16, ... यापुढील संख्या कोणती असेल?", listOf("A) 24","B) 30","C) 32","D) 40"),2),
            Question(8,"प्रश्न ८: तीन नावे एकाच वेळी नदीच्या पाण्यात उतरली. समजा, पाणी वर वर जाण्याच्या क्रमाने असलेल्या नावांचा क्रम 'C', 'B', 'A' आहे. तर सर्वात शेवटी कोणता नाव पाण्यात उतरेल?", listOf("A) A","B) B","C) C","D) नाव क्रमात नसतील"),0),
            Question(9,"प्रश्न ९: एका वेळी फक्त एका प्रश्नाचे उत्तर देता येईल. समजा, एका कोड्यात चार प्रश्न आहेत. यात उत्तर काढण्यासाठी किती वेळा प्रश्न विचारावे लागतील?", listOf("A) 1","B) 2","C) 3","D) 4"),3),
            Question(10,"प्रश्न १०: एका खोलीत 4 माणसे बसलेली आहेत. जर प्रत्येकाने प्रत्येकाशी हस्तांदोलन केले, तर एकूण हस्तांदोलन किती होतील?", listOf("A) 6","B) 12","C) 10","D) 8"),0),
            Question(11,"प्रश्न ११: ८, १२, २०, ३२ या अंकांत पुढील संख्या कोणती असेल?", listOf("A) ४८","B) ५०","C) ४२","D) ४०"),3),
            Question(12,"प्रश्न १२: ५ कि.मी. चे मीटर मध्ये रूपांतर करा.", listOf("A) ५०० मीटर","B) ५००० मीटर","C) ५५०० मीटर","D) ५०००० मीटर"),1),
            Question(13,"प्रश्न १३: १२x१२ किती आहे?", listOf("A) १४४","B) १२८","C) १५६","D) १३२"),0),
            Question(14,"प्रश्न १४: एका संख्येचे २०% म्हणजे ५० आहे. तर ती संख्या किती असेल?", listOf("A) २००","B) २५०","C) १००","D) १५०"),0),
            Question(15,"प्रश्न १५: एका वस्त्रावर ४०% सूट आहे, तर ५०० रुपयांच्या वस्त्रावर सवलतीनंतरची किंमत किती?", listOf("A) ३००","B) ४००","C) २००","D) ३५०"),0),
            Question(16,"प्रश्न १६: एका समभुज त्रिकोणातील प्रत्येक कोनाचे माप किती आहे?", listOf("A) 90°","B) 60°","C) 120°","D) 45°"),1),
            Question(17,"प्रश्न १७: एका त्रिकोणाच्या दोन बाजू अनुक्रमे 7 आणि 9 आहेत. जर तिसरी बाजू 12 असेल, तर त्रिकोणाचे क्षेत्रफळ शोधा. (हीरोनचे सूत्र वापरा)", listOf("A) 35","B) 42","C) 50","D) 60"),2),
            Question(18,"प्रश्न १८: एकतर्फी झगडल्यास किती वेळ लागतो?", listOf("A) २ तास","B) १ तास","C) ५ तास","D) ०.५ तास"),1),
            Question(19,"प्रश्न १९: बोटावरील संख्यांकाचे मान किती आहेत?", listOf("A) २","B) ०","C) १","D) ५"),1),
            Question(20,"प्रश्न २०: 'तामिळ' भाषा कोणत्या परिवारात येते?", listOf("A) इंडो-युरोपीय","B) द्रविड","C) सिमीत","D) ऑस्ट्रो-नेशियन"),1),
            Question(21,"प्रश्न २१: २०२४ च्या जी-२० शिखर परिषद कोणत्या शहरात झाली?", listOf("A) नवी दिल्ली","B) मुंबई","C) बेंगळुरू","D) कोलकाता"),0),
            Question(22,"प्रश्न २२: २०२४ च्या ऑलिंपिक खेळ कोणत्या देशात होणार आहेत?", listOf("A) जपान","B) अमेरिका","C) फ्रान्स","D) ऑस्ट्रेलिया"),2),
            Question(23,"प्रश्न २३: २०२४ मध्ये भारतातील पंतप्रधान निवडणुकीत कोणत्या पक्षाने सर्वाधिक जागा जिंकल्या?", listOf("A) भारतीय जनता पक्ष (BJP)","B) काँग्रेस","C) आम आदमी पक्ष","D) समाजवादी पक्ष"),0),
            Question(24,"प्रश्न २४: २०२४ मध्ये नासाच्या कोणत्या मोहिमेत मंगळ ग्रहाच्या वातावरणाबद्दल अधिक माहिती गोळा केली गेली?", listOf("A) मंगळ अभियान","B) पर्सेव्हरन्स रोव्हर","C) न्यू होरायझन्स","D) क्युरियोसिटी"),1),
            Question(25,"प्रश्न २५: २०२४ च्या नोबेल शांती पुरस्कार कोणाला मिळाला?", listOf("A) डेनिस मुकवेगे","B) मारिया रेसा","C) ग्रीटा थनबर्ग","D) यापैकी नाही"),2),
            Question(26,"Question 26: Choose the correct synonym for the word elated", listOf("A) Sad","B) Excited","C) Angry","D) Confused"),1),
            Question(27,"Question 27: Select the correct form of the verb to complete the sentence: \"He ______ to the market yesterday.\"", listOf("A) go","B) gone","C) went","D) going"),2),
            Question(28,"Question 28: Which of the following sentences is grammatically correct?", listOf("A) She don’t like pizza","B) She doesn’t likes pizza","C) She doesn’t like pizza","D) She didn’t liked pizza"),2),
            Question(29,"Question 29: Choose the word that best completes the sentence: \"The book on the table is ______.\"", listOf("A) your","B) yours","C) you","D) you're"),1),
            Question(30,"Question 30: Identify the part of speech of the underlined word: \"She quickly finished her homework.\"", listOf("A) Adverb","B) Verb","C) Noun","D) Adjective"),0)
        )
    }
}

