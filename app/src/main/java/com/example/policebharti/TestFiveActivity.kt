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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TestFiveActivity : AppCompatActivity() {

    private var currentQuestionIndex = 0
    private var score = 0
    private lateinit var questionList: List<Question>
    private lateinit var back: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_test_five)
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
                showScoreDialog(score)
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
            Question(1, "प्रश्न १: 'कवी राजा' कोण आहे?", listOf("A) कुमार गंधर्व", "B) सावरकर", "C) भास्कर", "D) संत तुकाराम"), 0),
            Question(2, "प्रश्न २: 'महाभारत' या ग्रंथात किती अध्याय आहेत?", listOf("A) 18", "B) 24", "C) 22", "D) 12"), 0),
            Question(3, "प्रश्न ३: 'राजर्षी शाहू' यांच्या कार्याबद्दल कोणती माहिती खोटी आहे?", listOf("A) त्यांनी शिक्षणासाठी विशेष प्रयत्न केले", "B) त्यांनी वडगाव कारागीरला माघार दिली", "C) त्यांनी अनाथालयाची स्थापना केली", "D) त्यांनी सामाजिक समता साधली"), 1),
            Question(4, "प्रश्न ४: 'शंभर निबंध' ह्या पुस्तकाचे लेखक कोण?", listOf("A) कवींद्रनाथ ठाकूर", "B) पु. ना. देशपांडे", "C) व. पु. काळे", "D) शं. ना. नवरे"), 1),
            Question(5, "प्रश्न ५: 'मराठी भाषा' कोणत्या वंशाची आहे?", listOf("A) द्रविड", "B) आर्य", "C) मुघल", "D) पारसी"), 1),

            // Ankaganit (Mathematics) Questions
            Question(6, "प्रश्न ६: १ ते १० पर्यंतच्या सर्व संख्यांचा योग किती आहे?", listOf("A) 45", "B) 50", "C) 55", "D) 60"), 2),
            Question(7, "प्रश्न ७: कोणता आकृती १ सेमी = 10 मिमी", listOf("A) 1m", "B) 1cm", "C) 1km", "D) 1mm"), 1),
            Question(8, "प्रश्न ८: कोणत्या समीकरणाची उत्तरं सर्व वास्तविक संख्यांमध्ये असतात?", listOf("A) x^2 + 1 = 0", "B) x^2 - 1 = 0", "C) x^2 + x + 1 = 0", "D) x^2 - 2x + 1 = 0"), 1),
            Question(9, "प्रश्न ९: कोणत्या आकृतीमध्ये 360 अंश कोन असतो?", listOf("A) त्रिकोण", "B) चतुरस्र", "C) पेंटागोन", "D) हेक्सागोन"), 1),
            Question(10, "प्रश्न १०: 16 चा वर्गमूळ काय आहे?", listOf("A) 3", "B) 4", "C) 5", "D) 6"), 1),

            // Buddhimatta Chachni (Logical Reasoning) Questions
            Question(11, "प्रश्न ११: 'सर्व संगीतकार कवी आहेत', ह्या विधानाचे विलोम विधान काय?", listOf("A) सर्व कवी संगीतकार आहेत", "B) काही संगीतकार कवी नाहीत", "C) काही कवी संगीतकार आहेत", "D) सर्व संगीतकार नाहीत"), 1),
            Question(12, "प्रश्न १२: A आणि B दोन्ही खरे असल्यास, 'A आणि B' हे विधान काय असते?", listOf("A) खरे", "B) खोटे", "C) कदाचित खरे", "D) अपूर्ण"), 0),
            Question(13, "प्रश्न १३: 'यदि P, तर Q' या विधानात P चा खोटा अर्थ असला तरी Q कसा असेल?", listOf("A) खरे", "B) खोटे", "C) अव्यवस्थित", "D) अनिश्चित"), 1),
            Question(14, "प्रश्न १४: 'सर्व कुक्कुट उड्डाण करू शकतात', हे विधान खरे असल्यास, हे विधान कोणते?", listOf("A) काही कुक्कुट उड्डाण करत नाहीत", "B) काही कुक्कुट उड्डाण करू शकतात", "C) सर्व कुक्कुट उड्डाण करू शकतात", "D) कुक्कुट उड्डाण करत नाहीत"), 1),
            Question(15, "प्रश्न १५: 'रोज मळणी करणारा तांदूळ पकडतो' ह्या विधानातील अटी कोणती?", listOf("A) मळणी", "B) पकडणे", "C) कामगार", "D) तांदूळ"), 1),

            Question(16, "प्रश्न 16: ‘श्रीमान योगी’ या पुस्तकाचे लेखक कोण आहेत?", listOf("A) शिवाजी सावंत", "B) रणजीत देसाई", "C) विष्णू सखाराम खांडेकर", "D) वि. स. खांडेकर"), 1),
            Question(17, "प्रश्न 17: संत तुकारामांच्या अभंगांमध्ये 'देहू' या गावाचे महत्व काय आहे?", listOf("A) तुकारामांचे जन्मस्थान", "B) तुकारामांचे कार्यक्षेत्र", "C) तुकारामांची समाधी", "D) सर्व पर्याय बरोबर आहेत"), 3),
            Question(18, "प्रश्न 18: ‘माझा प्रिय मित्र’ हा निबंध कोणत्या लेखकाने लिहिला आहे?", listOf("A) वि. स. खांडेकर", "B) पु. ल. देशपांडे", "C) ग. दि. माडगूळकर", "D) बा. सी. मर्ढेकर"), 1),
            Question(19, "प्रश्न 19: 'गीतरामायण' या काव्याचे रचनाकार कोण?", listOf("A) शंकर पाटील", "B) वि. स. खांडेकर", "C) ग. दि. माडगूळकर", "D) ना. सि. फडके"), 2),
            Question(20, "प्रश्न 20: ‘किर्लोस्कर’ हे कोणत्या साहित्य प्रकारासाठी प्रसिद्ध मासिक होते?", listOf("A) कथा", "B) कविता", "C) नाटक", "D) लेख"), 2),


                    // Chalu Ghadamodi (Current Affairs) Questions
            Question(21, "प्रश्न २१: २०२४ च्या आम निवडणुकांसाठी प्रमुख नेता कोण?", listOf("A) नरेंद्र मोदी", "B) राहुल गांधी", "C) ममता बॅनर्जी", "D) अरविंद केजरीवाल"), 0),
            Question(22, "प्रश्न २२: 'शांती पुरस्कार' २०२३ मध्ये कोणाला मिळाला?", listOf("A) डेविड अटेंबरो", "B) मलाला यूसुफजई", "C) वांगारी माथाई", "D) बान की मून"), 1),
            Question(23, "प्रश्न २३: २०२३ च्या 'इंटरनॅशनल गेम्स'मध्ये कोणती खेळ अधिक प्रसिद्ध झाली?", listOf("A) क्रिकेट", "B) बास्केटबॉल", "C) तिरंदाजी", "D) भालाफेक"), 1),
            Question(24, "प्रश्न २४: २०२२ च्या 'फिफा वर्ल्ड कप'मध्ये कोणती संघ विजयी झाली?", listOf("A) ब्राझील", "B) अर्जेंटिना", "C) जर्मनी", "D) फ्रान्स"), 1),
            Question(25, "प्रश्न २५: २०२२ च्या 'इंडियन ऑलिम्पिक' खेळात कोणती खेळाडू सर्वात यशस्वी ठरली?", listOf("A) पी. व्ही. सिंधू", "B) साक्षी मलिक", "C) नीरज चोप्रा", "D) मीराबाई चानू"), 2),

            Question(26, "प्रश्न 26: 50 च्या पाचपटात किती आहे?", listOf("A) 200", "B) 250", "C) 300", "D) 150"), 1),
            Question(27, "प्रश्न 27: 81 चे वर्गमूळ घेतल्यास किती येते?", listOf("A) 7", "B) 8", "C) 9", "D) 10"), 2),
            Question(28, "प्रश्न 28: 2, 4, 8, 16 या श्रेणीतील पुढील संख्या कोणती?", listOf("A) 20", "B) 24", "C) 32", "D) 30"), 2),
            Question(29, "प्रश्न 29: एका आयताचे क्षेत्रफळ काढण्यासाठी योग्य सूत्र काय आहे?", listOf("A) लांबी x रुंदी", "B) 2 x लांबी", "C) लांबी + रुंदी", "D) लांबी - रुंदी"), 0),
            Question(30, "प्रश्न 30: 6, 12, 18, 24 या श्रेणीतील पुढील संख्या कोणती?", listOf("A) 28", "B) 30", "C) 32", "D) 36"), 3)

        )
    }

    private fun showScoreDialog(score: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Quiz Finished!")
        builder.setMessage("Your score is $score/${questionList.size}")

        // Add a button to reset the quiz
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
            resetQuiz() // Reset the quiz after dismissing the dialog
        }

        // Create and show the dialog
        val dialog = builder.create()
        dialog.show()
    }
}
