package com.example.policebharti

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class GanitActivity : AppCompatActivity() {

    private var currentQuestionIndex = 0
    private var score = 0
    private lateinit var questionList: List<Question>
    private lateinit var back: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ganit)
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
            val correctAnswerText = findViewById<RadioButton>(
                questionList[currentQuestionIndex].correctAnswerIndex
            ).text.toString()

            if (isCorrect) {
                score++ // Increase the score for correct answer
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show()
            }

            currentQuestionIndex++
            val radioGroup = findViewById<RadioGroup>(R.id.rgOptions)
            radioGroup.clearCheck()

            if (currentQuestionIndex < questionList.size) {
                displayQuestion(currentQuestionIndex)
                tv.text = "${currentQuestionIndex + 1}"
            } else {
                // End of quiz
                Toast.makeText(this,"Quiz Ended!",Toast.LENGTH_SHORT).show()
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

        val primaryColor = ContextCompat.getColor(this, R.color.black)
        question.options.forEachIndexed { i, option ->
            val radioButton = AppCompatRadioButton(this)
            radioButton.text = option
            radioButton.id = i
            radioButton.setTextColor(Color.BLACK)
            radioButton.buttonTintList = ColorStateList.valueOf(primaryColor)
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
            Question(1, "प्रश्न १: दोन संख्यांचा गुणोत्तर 4:5 आहे. जर त्यांची बेरीज 180 असेल, तर त्या संख्यांचा गुणाकार किती आहे?", listOf("A) 7200", "B) 720", "C) 3600", "D) 1800"), 0),
            Question(2, "प्रश्न २: एका वस्तूची किंमत 25% नी वाढल्यानंतर ती किंमत 500 रुपये झाली. तर वस्तूची मूळ किंमत किती?", listOf("A) 400", "B) 450", "C) 375", "D) 425"), 0),
            Question(3, "प्रश्न ३: 14 च्या वर्गमूळात किती वाढ केली तर उत्तर 169 येते?", listOf("A) 11", "B) 13", "C) 12", "D) 15"), 1),
            Question(4, "प्रश्न ४: 12 वेळा एका संख्येला गुणले की 144 येते, तर ती संख्या कोणती?", listOf("A) 10", "B) 12", "C) 15", "D) 18"), 1),
            Question(5, "प्रश्न ५: एका प्रवासी वाहनाने 300 किमी अंतर 6 तासांत पूर्ण केले. त्याचा सरासरी वेग किती किमी/तास आहे?", listOf("A) 60", "B) 50", "C) 75", "D) 90"), 2),
            Question(6, "प्रश्न ६: एका संख्येच्या वर्गाची बेरीज 169 आहे. तर ती संख्या कोणती?", listOf("A) 12", "B) 11", "C) 13", "D) 10"), 2),
            Question(7, "प्रश्न ७: एका कामगाराने 10 दिवसांत काम संपवले, जर कामगाराचे दिवसाच्या संख्येत 50% वाढ केली तर काम पूर्ण करण्यास किती दिवस लागतील?", listOf("A) 15", "B) 20", "C) 12", "D) 25"), 0),
            Question(8, "प्रश्न ८: 60% एका संख्येचे 80 आहे. तर ती संख्या कोणती?", listOf("A) 120", "B) 160", "C) 100", "D) 140"), 0),
            Question(9, "प्रश्न ९: एका वस्तूच्या किंमतीत 10% वाढ केली तर नवीन किंमत 330 रुपये झाली. मूळ किंमत किती?", listOf("A) 300", "B) 310", "C) 290", "D) 305"), 0),
            Question(10, "प्रश्न १०: दोन संख्यांचा गुणाकार 252 आहे आणि त्यांचा HCF 6 आहे, तर त्यांचा LCM किती आहे?", listOf("A) 42", "B) 36", "C) 48", "D) 60"), 0),
            Question(11, "प्रश्न ११: एका संख्या x च्या वर्गात 4 कमी केले तर 225 येते. x ची किंमत काय आहे?", listOf("A) 15", "B) 17", "C) 16", "D) 14"), 2),
            Question(12, "प्रश्न १२: एका वस्तूची किंमत 20% कमी केली तर ती किंमत 400 रुपये झाली. मूळ किंमत किती?", listOf("A) 480", "B) 500", "C) 450", "D) 520"), 1),
            Question(13, "प्रश्न १३: तीन संख्यांचे सरासरी 50 आहे. पहिल्या आणि दुसऱ्या संख्येची बेरीज 80 आहे, तर तिसरी संख्या किती आहे?", listOf("A) 40", "B) 45", "C) 30", "D) 35"), 3),
            Question(14, "प्रश्न १४: 9 च्या पाचपटीत 36 कमी केले तर किती शिल्लक राहील?", listOf("A) 9", "B) 27", "C) 15", "D) 18"), 2),
            Question(15, "प्रश्न १५: एका वस्तूवर 25% नफा झाला. जर वस्तूची किंमत 120 रुपये असेल तर नफ्याची रक्कम किती?", listOf("A) 30", "B) 25", "C) 15", "D) 20"), 1),
            Question(16, "प्रश्न १६: 8 आणि 12 च्या गुणाकारातील सर्वात कमी सामान्य गुणक (LCM) किती आहे?", listOf("A) 48", "B) 24", "C) 36", "D) 40"), 0),
            Question(17, "प्रश्न १७: एका संख्येचा 2 ने घटक काढल्यावर 27 उरतो. तर ती संख्या कोणती?", listOf("A) 54", "B) 56", "C) 52", "D) 58"), 0),
            Question(18, "प्रश्न १८: एका वस्तूची किंमत 2400 रुपये आहे. जर त्या किंमतीवर 5% सूट दिली तर सूट किती रुपये असेल?", listOf("A) 120", "B) 150", "C) 100", "D) 140"), 2),
            Question(19, "प्रश्न १९: एका पाच अंकी संख्येत पहिले दोन अंक 45 आणि शेवटचे तीन अंक 678 आहेत. तर ती संपूर्ण संख्या किती आहे?", listOf("A) 45678", "B) 34678", "C) 25678", "D) 15678"), 0),
            Question(20, "प्रश्न २०: एका चौरसाची बाजू 6 मीटर आहे. तर त्या चौरसाचे क्षेत्रफळ किती?", listOf("A) 36 वर्ग मीटर", "B) 30 वर्ग मीटर", "C) 24 वर्ग मीटर", "D) 40 वर्ग मीटर"), 0),
            Question(21, "प्रश्न २१: दोन समांतर रेषा एका समानान्तर चतुर्भुजाच्या कर्णांना जोडतात. या कर्णांची बेरीज 180 अंश आहे. कर्णाचा प्रत्येक कोन किती आहे?", listOf("A) 90 अंश", "B) 80 अंश", "C) 70 अंश", "D) 60 अंश"), 0),
            Question(22, "प्रश्न २२: 45 किमी/तासाने एका वाहनाने 3 तास प्रवास केला. त्याचे संपूर्ण अंतर किती आहे?", listOf("A) 135 किमी", "B) 140 किमी", "C) 130 किमी", "D) 150 किमी"), 0),
            Question(23, "प्रश्न २३: एका वर्गाच्या चार बाजू 10 मीटर लांब आहेत. तर त्याचे परिघ किती आहे?", listOf("A) 40 मीटर", "B) 45 मीटर", "C) 50 मीटर", "D) 55 मीटर"), 0),
            Question(24, "प्रश्न २४: एका 12 अंकी क्रमांकाची पहिली 6 अंकांमध्ये शून्य नाही. या क्रमांकाचा शेवट कोणत्या अंकाने होईल?", listOf("A) 5", "B) 4", "C) 6", "D) 8"), 2),
            Question(25, "प्रश्न २५: एका प्रवासात 2/3 अंतर चालून झाल्यानंतर 1/3 अंतर शिल्लक राहिले आहे. पूर्ण अंतर किती?", listOf("A) 1", "B) 3", "C) 2", "D) 4"), 1),
            Question(26, "प्रश्न २६: एका गोदामात 3 गोड्या आहेत. 12 आणि 18 आंब्यांची 3 शंका एकमेकांमध्ये मिसळल्या आहेत. तर प्रत्येक शंकेतील आंब्यांची संख्या किती?", listOf("A) 14", "B) 15", "C) 16", "D) 18"), 1),
            Question(27, "प्रश्न २७: कोणती संख्या 7 ची गाणी आहे?", listOf("A) 42", "B) 49", "C) 56", "D) 63"), 1),
            Question(28, "प्रश्न २८: कोणत्या संख्येच्या वर्गाचे उत्तर 121 आहे?", listOf("A) 10", "B) 11", "C) 12", "D) 13"), 1),
            Question(29, "प्रश्न २९: एका संख्येतून 5 कमी केल्यास 15 राहते. ती संख्या कोणती?", listOf("A) 20", "B) 22", "C) 18", "D) 25"), 0),
            Question(30, "प्रश्न ३०: एका बागेत 25 मीटर लांबीची 20 झाडे आहेत. संपूर्ण बागेचे क्षेत्रफळ किती?", listOf("A) 500 वर्ग मीटर", "B) 600 वर्ग मीटर", "C) 400 वर्ग मीटर", "D) 450 वर्ग मीटर"), 2),
            Question(31, "प्रश्न ३१: एका व्यक्तीने 750 रुपयात एक वस्तू विकली आणि 25% नफा कमावला. वस्तूची मूळ किंमत किती आहे?", listOf("A) 600", "B) 550", "C) 500", "D) 625"), 0),
            Question(32, "प्रश्न ३२: एका ठराविक प्रमाणात व्याज मिळवण्यासाठी 5 वर्षांत एक रक्कम दुप्पट होते. तर किती वर्षांत ती रक्कम चौपट होईल?", listOf("A) 10", "B) 15", "C) 20", "D) 25"), 1),
            Question(33, "प्रश्न ३३: एका कुटुंबाचे महिन्याचे सरासरी खर्च 25000 रुपये आहेत. जर दरमहा 5% खर्च वाढत असेल, तर 6 महिन्यांनंतर त्यांचा मासिक खर्च किती असेल?", listOf("A) 32000", "B) 28000", "C) 31857", "D) 31250"), 2),
            Question(34, "प्रश्न ३४: 20 विद्यार्थ्यांचे गुणांची सरासरी 75 आहे. जर एका विद्यार्थ्याचे गुण 95 असतील, तर नवीन सरासरी किती असेल?", listOf("A) 76", "B) 77", "C) 78", "D) 79"), 1),
            Question(35, "प्रश्न ३५: एका संख्येच्या वर्गाची बेरीज 289 आहे. तर ती संख्या कोणती?", listOf("A) 17", "B) 18", "C) 19", "D) 20"), 0),
            Question(36, "प्रश्न ३६: 10 वर्षांपूर्वी एका व्यक्तीचे वय 30 वर्ष होते. त्याचे वर्तमान वय किती?", listOf("A) 40", "B) 45", "C) 50", "D) 55"), 0),
            Question(37, "प्रश्न ३७: 18 आणि 12 या संख्यांचा LCM (लघुत्तम समान गुणक) किती आहे?", listOf("A) 36", "B) 48", "C) 60", "D) 72"), 0),
            Question(38, "प्रश्न ३८: 40 विद्यार्थ्यांपैकी 8 विद्यार्थी एका विशिष्ट परीक्षेत नापास झाले. नापास विद्यार्थ्यांचे टक्केवारी किती आहे?", listOf("A) 20%", "B) 15%", "C) 25%", "D) 30%"), 0),
            Question(39, "प्रश्न ३९: 100 किमी अंतर प्रवास करण्यासाठी एका वाहनाला 4 तास लागले. तर वाहनाचा सरासरी वेग किती आहे?", listOf("A) 25 किमी/तास", "B) 30 किमी/तास", "C) 35 किमी/तास", "D) 40 किमी/तास"), 3),
            Question(40, "प्रश्न ४०: एका वस्तूची किंमत 20% ने घटल्यानंतर ती किंमत 320 रुपये झाली. मूळ किंमत किती होती?", listOf("A) 400", "B) 375", "C) 340", "D) 360"), 0),
            Question(41, "प्रश्न ४१: एका वर्गाच्या बाजूची लांबी 14 मीटर आहे. तर त्याचा परिघ किती आहे?", listOf("A) 56 मीटर", "B) 60 मीटर", "C) 48 मीटर", "D) 50 मीटर"), 0),
            Question(42, "प्रश्न ४२: 15 वर्षांपूर्वी एका व्यक्तीचे वय त्याच्या वडिलांच्या वयाच्या अर्धे होते. जर वडिलांचे वर्तमान वय 60 वर्ष असेल, तर त्या व्यक्तीचे वय किती आहे?", listOf("A) 30", "B) 35", "C) 40", "D) 45"), 1),
            Question(43, "प्रश्न ४३: 45 किलोमीटर अंतर एका वाहनाने 1.5 तासात पार केले. त्याचा सरासरी वेग किती किमी/तास आहे?", listOf("A) 25", "B) 30", "C) 35", "D) 40"), 1),
            Question(44, "प्रश्न ४४: एका संख्येचा 40% भाग 200 आहे. तर ती संपूर्ण संख्या किती आहे?", listOf("A) 500", "B) 400", "C) 300", "D) 600"), 0),
            Question(45, "प्रश्न ४५: एका कुटुंबाचे 6 महिन्यांचे सरासरी मासिक खर्च 20,000 रुपये आहेत. या कुटुंबाचा एकूण 6 महिन्यांचा खर्च किती असेल?", listOf("A) 1,20,000", "B) 1,50,000", "C) 1,00,000", "D) 1,30,000"), 0),
            Question(46, "प्रश्न ४६: एका त्रिकोणाच्या तीन बाजू 6 मीटर, 8 मीटर, आणि 10 मीटर आहेत. या त्रिकोणाचे क्षेत्रफळ किती?", listOf("A) 24 चौरस मीटर", "B) 48 चौरस मीटर", "C) 36 चौरस मीटर", "D) 30 चौरस मीटर"), 0),
            Question(47, "प्रश्न ४७: एका संख्येचे 25% भाग 150 आहे. तर ती संख्या कोणती?", listOf("A) 600", "B) 500", "C) 450", "D) 550"), 1),
            Question(48, "प्रश्न ४८: 120 रुपये वजा करून मिळवलेल्या किंमतीवर 10% सूट दिल्यास अंतिम किंमत 450 रुपये येते. मूळ किंमत किती होती?", listOf("A) 600", "B) 700", "C) 650", "D) 500"), 0),
            Question(49, "प्रश्न ४९: एका कामगाराने 8 तास काम केले, आणि त्याला 400 रुपये मिळाले. तर त्याचे एका तासाचे वेतन किती आहे?", listOf("A) 40", "B) 50", "C) 60", "D) 80"), 1),
            Question(50, "प्रश्न ५०: एका संख्येचा 3 ने घटक काढल्यावर 27 उरतो. तर ती संख्या कोणती आहे?", listOf("A) 81", "B) 75", "C) 90", "D) 100"), 0)
        )
    }

}
