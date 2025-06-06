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

class BuddhimattaQuestionsActivity : AppCompatActivity() {

    private var currentQuestionIndex = 0
    private var score = 0
    private lateinit var questionList: List<Question>
    private lateinit var back: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_buddhimatta_questions)
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
                Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show()
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

    private fun loadQuestions(): List<Question> {
        return listOf(
            // Buddhimatta Chachni (Logical Reasoning) Questions
            Question(1, "प्रश्न १: एका बसस्टॉपवर तीन मित्र थांबले आहेत. पहिला मित्र सर्वात उंच आहे, दुसरा सर्वात लहान आहे आणि तिसरा त्या दोघांच्या मधला आहे. तिसरा मित्र कोण आहे?", listOf("A) उंच", "B) मध्यम", "C) लहान", "D) कोणताच नाही"), 1),
            Question(2, "प्रश्न २: जर CAT चे अंक 312 असतील, तर DOG चे अंक काय असतील?", listOf("A) 415", "B) 417", "C) 412", "D) 416"), 2),
            Question(3, "प्रश्न ३: 'सोनू कोणाच्या बाजूला बसला आहे?' हा प्रश्नाचा उत्तर कोणत्या घटकावर अवलंबून आहे?", listOf("A) स्थान", "B) वेळ", "C) व्यक्ती", "D) क्रम"), 0),
            Question(4, "प्रश्न ४: एका संख्येत 3 घटक असतात: दुसऱ्या संख्येत हेच घटक असतात पण वेगळ्या क्रमात. त्याचे नाव सांगा.", listOf("A) परिमाण", "B) गुणोत्तर", "C) स्थानवळण", "D) अनुक्रम"), 3),
            Question(5, "प्रश्न ५: 'रामने एका गडाच्या चढाईच्या वेळेत अर्धा वेळ पूर्ण केला. आता उर्वरित किती वेळ लागेल?' हे कसे ठरवायचे?", listOf("A) वेळ गुणिले 2", "B) वेळ वजा 2", "C) वेळ भागिले 2", "D) वेळ अधिक 2"), 0),
            Question(6, "प्रश्न ६: जर 2, 4, 8, 16 अशा क्रमाने संख्या वाढत असतील, तर पुढील संख्या कोणती येईल?", listOf("A) 32", "B) 24", "C) 64", "D) 40"), 0),
            Question(7, "प्रश्न ७: एका कुटुंबात 6 सदस्य आहेत. A हा B चा भाऊ आहे, C हा B चा पती आहे, D हा C चा मुलगा आहे. तर D चे B शी नाते काय?", listOf("A) मुलगी", "B) मुलगा", "C) मुलगी किंवा मुलगा नाही", "D) भाऊ"), 1),
            Question(8, "प्रश्न ८: जर एका घड्याळात सकाळचे 10 वाजले असतील आणि ते 30 मिनिटे पुढे धावत असेल, तर 4 तासानंतर ते किती वाजवेल?", listOf("A) 2:30 PM", "B) 1:30 PM", "C) 3:00 PM", "D) 2:00 PM"), 0),
            Question(9, "प्रश्न ९: एका साखळीमध्ये P, Q, R, S, T असे 5 शब्द आहेत. Q हा R च्या डाव्या बाजूला आहे, P हा Q च्या उजव्या बाजूला आहे, S हा T च्या उजव्या बाजूला आहे. कोणता शब्द साखळीच्या मध्यभागी आहे?", listOf("A) Q", "B) P", "C) R", "D) S"), 2),
            Question(10, "प्रश्न १०: जर एका कक्षेत 40 विद्यार्थी आहेत आणि प्रत्येक विद्यार्थ्याला एका चॉकोलेट देण्यात आले, तर चॉकोलेटची संख्या किती असेल?", listOf("A) 40", "B) 80", "C) 60", "D) 100"), 0),
            Question(11, "प्रश्न ११: राम, श्याम आणि सुरेश हे तीन मित्र आहेत. राम हा श्यामपेक्षा उंच आहे, पण सुरेश हा रामपेक्षा उंच आहे. तर कोण सर्वात उंच आहे?", listOf("A) राम", "B) श्याम", "C) सुरेश", "D) समान आहेत"), 2),
            Question(12, "प्रश्न १२: जर एका पुस्तकाचे 60 पान असतील आणि प्रत्येक पृष्ठावर 30 ओळी असतील, तर एकूण ओळींची संख्या किती?", listOf("A) 1800", "B) 1200", "C) 1500", "D) 2000"), 0),
            Question(13, "प्रश्न १३: जर A हा B चा भाऊ असेल आणि C हा B चा पती असेल, तर A चे C शी नाते काय?", listOf("A) पती", "B) भाऊ", "C) भाऊ किंवा पती नाही", "D) मित्र"), 2),
            Question(14, "प्रश्न १४: 15, 30, 45 अशा क्रमाने संख्येत वाढ होत असेल, तर पुढील संख्या कोणती येईल?", listOf("A) 55", "B) 60", "C) 75", "D) 65"), 1),
            Question(15, "प्रश्न १५: जर 2 = 4, 3 = 9, 4 = 16 असे असेल, तर 5 = ?", listOf("A) 20", "B) 30", "C) 25", "D) 35"), 2),
            Question(16, "प्रश्न १६: एका घड्याळात सकाळी 6 वाजले असताना, 9 तासानंतर घड्याळ किती वाजवेल?", listOf("A) 3:00 PM", "B) 4:00 PM", "C) 2:00 PM", "D) 6:00 PM"), 0),
            Question(17, "प्रश्न १७: जर 5 × 2 = 10 आणि 4 × 3 = 12 असेल, तर 7 × 2 = ?", listOf("A) 12", "B) 14", "C) 16", "D) 18"), 1),
            Question(18, "प्रश्न १८: एका कामात 5 लोक 10 दिवस काम करतात. 10 लोकांनी तेच काम किती दिवसात पूर्ण करावे?", listOf("A) 5 दिवस", "B) 2 दिवस", "C) 10 दिवस", "D) 15 दिवस"), 0),
            Question(19, "प्रश्न १९: जर एका वर्गात 8 मुली आहेत आणि प्रत्येक मुलीला दोन पेन्सिल दिल्या जात आहेत, तर पेन्सिलची एकूण संख्या किती?", listOf("A) 16", "B) 8", "C) 12", "D) 20"), 0),
            Question(20, "प्रश्न २०: एका पुस्तकाच्या प्रत्येक पृष्ठावर 24 शब्द आहेत आणि पुस्तकात एकूण 50 पृष्ठे आहेत, तर पुस्तकातील शब्दांची एकूण संख्या किती?", listOf("A) 1200", "B) 1500", "C) 1000", "D) 1250"), 0),
            Question(21, "प्रश्न २१: जर एका संख्येत 4 घटक असतील, तर त्याचे चौकटीकरण कसे कराल?", listOf("A) गुणिले 2", "B) भागिले 2", "C) घटले 2", "D) अधिक 2"), 1),
            Question(22, "प्रश्न २२: एका वर्गात 30 विद्यार्थी आहेत, त्यातील 12 विद्यार्थी मुली आहेत. मुलांची संख्या किती आहे?", listOf("A) 18", "B) 12", "C) 20", "D) 15"), 0),
            Question(23, "प्रश्न २३: एका रांगेत 6 मुले आणि 5 मुली आहेत. जर प्रत्येक मुलाने एका मुलीला जोडीदार घेतला, तर किती मुली उरतील?", listOf("A) 1", "B) 2", "C) 3", "D) कोणताच नाही"), 0),
            Question(24, "प्रश्न २४: एका कंपनीमध्ये 8 कर्मचारी काम करतात. त्यातील 2 कर्मचारी महिला आहेत. पुरुषांची संख्या किती आहे?", listOf("A) 6", "B) 8", "C) 4", "D) 5"), 0),
            Question(25, "प्रश्न २५: जर एका संख्येचे 25% 10 असेल, तर संख्येची एकूण किंमत काय असेल?", listOf("A) 20", "B) 30", "C) 40", "D) 50"), 2),
            Question(26, "प्रश्न २६: एका तासाच्या 60 मिनिटांपैकी एका कार्यासाठी 15 मिनिटे लागली. किती टक्के वेळ खर्च झाला?", listOf("A) 15%", "B) 25%", "C) 50%", "D) 30%"), 1),
            Question(27, "प्रश्न २७: 10 चॉकलेटपैकी 6 चॉकलेट वितरित केली, तर शिल्लक चॉकलेटची संख्या किती?", listOf("A) 4", "B) 5", "C) 6", "D) 3"), 0),
            Question(28, "प्रश्न २८: एका कक्षेत 5 बाके आहेत आणि प्रत्येक बाकावर 4 विद्यार्थी बसले आहेत, तर एकूण विद्यार्थ्यांची संख्या किती?", listOf("A) 15", "B) 20", "C) 10", "D) 25"), 1),
            Question(29, "प्रश्न २९: एका सरळ रेषेत 6 मुले आणि 4 मुली उभ्या आहेत. मुली कितव्या स्थानावर आहेत?", listOf("A) 4", "B) 6", "C) 2", "D) 1"), 2),
            Question(30, "प्रश्न ३०: एका वर्गात 40 विद्यार्थी आहेत. जर त्यातील 25 विद्यार्थी उत्तीर्ण झाले असतील, तर किती विद्यार्थी नापास झाले?", listOf("A) 10", "B) 15", "C) 20", "D) 25"), 1),
            Question(31, "प्रश्न ३१: एका पेटीमध्ये 50 फळे आहेत. त्यापैकी 20 सफरचंदे आहेत. सफरचंदांची संख्या किती आहे?", listOf("A) 30", "B) 20", "C) 40", "D) 25"), 1),
            Question(32, "प्रश्न ३२: जर एका वर्गात 5 बाके असतील आणि प्रत्येक बाकावर 3 विद्यार्थी बसले असतील, तर एकूण विद्यार्थ्यांची संख्या किती?", listOf("A) 10", "B) 15", "C) 20", "D) 25"), 1),
            Question(33, "प्रश्न ३३: एका कक्षेत 10 विद्यार्थी आहेत, त्यातील 4 विद्यार्थी उंच आहेत. उंच नसलेल्या विद्यार्थ्यांची संख्या किती?", listOf("A) 6", "B) 4", "C) 10", "D) 8"), 0),
            Question(34, "प्रश्न ३४: एका रांगेत 6 मुले आणि 5 मुली आहेत. जर प्रत्येक मुलाने एका मुलीला जोडीदार घेतला, तर किती मुली उरतील?", listOf("A) 1", "B) 2", "C) 3", "D) कोणताच नाही"), 0),
            Question(35, "प्रश्न ३५: जर 5 × 2 = 10 आणि 4 × 3 = 12 असेल, तर 7 × 2 = ?", listOf("A) 14", "B) 16", "C) 18", "D) 20"), 0),
            Question(36, "प्रश्न ३६: एका कुटुंबात 8 सदस्य आहेत. त्यातील 4 सदस्य महिला आहेत. पुरुषांची संख्या किती?", listOf("A) 4", "B) 6", "C) 3", "D) 5"), 0),
            Question(37, "प्रश्न ३७: एका कक्षेत 20 विद्यार्थी आहेत. त्यापैकी 15 विद्यार्थी उपस्थित आहेत. अनुपस्थित विद्यार्थ्यांची संख्या किती?", listOf("A) 5", "B) 10", "C) 15", "D) 20"), 0),
            Question(38, "प्रश्न ३८: एका दुकानात 12 पेन्सिल आहेत. त्यापैकी 7 पेन्सिल विकल्या गेल्या. शिल्लक पेन्सिलची संख्या किती आहे?", listOf("A) 5", "B) 7", "C) 10", "D) 12"), 0),
            Question(39, "प्रश्न ३९: जर एका तासाच्या 60 मिनिटांपैकी 45 मिनिटे घेतली गेली असतील, तर शिल्लक वेळ किती आहे?", listOf("A) 15 मिनिटे", "B) 30 मिनिटे", "C) 45 मिनिटे", "D) 60 मिनिटे"), 0),
            Question(40, "प्रश्न ४०: एका वर्गात 35 विद्यार्थी आहेत. त्यातील 20 मुलगे आणि 15 मुली आहेत. मुलींची संख्या किती आहे?", listOf("A) 15", "B) 20", "C) 25", "D) 35"), 0),
            Question(41, "प्रश्न ४१: एका कुटुंबात 6 सदस्य आहेत. A हा B चा भाऊ आहे, C हा B चा पती आहे, D हा C चा मुलगा आहे. तर D चे B शी नाते काय?", listOf("A) मुलगी", "B) मुलगा", "C) मुलगी किंवा मुलगा नाही", "D) भाऊ"), 1),
            Question(42, "प्रश्न ४२: जर एका कक्षेत 20 बाके असतील आणि प्रत्येक बाकावर 2 विद्यार्थी बसले असतील, तर एकूण विद्यार्थ्यांची संख्या किती?", listOf("A) 30", "B) 40", "C) 20", "D) 50"), 1),
            Question(43, "प्रश्न ४३: एका पेटीत 20 फळे आहेत. त्यापैकी 10 सफरचंद आहेत. सफरचंद नसलेल्या फळांची संख्या किती आहे?", listOf("A) 10", "B) 15", "C) 20", "D) 25"), 0),
            Question(44, "प्रश्न ४४: एका वर्गात 25 विद्यार्थी आहेत. त्यापैकी 15 विद्यार्थी मुलगे आहेत. मुलींची संख्या किती आहे?", listOf("A) 10", "B) 15", "C) 20", "D) 25"), 0),
            Question(45, "प्रश्न ४५: एका वर्गात 5 बाके आहेत आणि प्रत्येक बाकावर 3 विद्यार्थी बसले आहेत, तर एकूण विद्यार्थ्यांची संख्या किती?", listOf("A) 15", "B) 25", "C) 20", "D) 10"), 0),
            Question(46, "प्रश्न ४६: एका तासाच्या 60 मिनिटांपैकी एका कार्यासाठी 30 मिनिटे लागली. किती टक्के वेळ खर्च झाला?", listOf("A) 30%", "B) 50%", "C) 60%", "D) 70%"), 1),
            Question(47, "प्रश्न ४७: जर एका संख्येचे 50% 20 असेल, तर संख्येची एकूण किंमत काय असेल?", listOf("A) 40", "B) 30", "C) 50", "D) 20"), 0),
            Question(48, "प्रश्न ४८: एका वर्गात 10 बाके आहेत आणि प्रत्येक बाकावर 2 विद्यार्थी बसले आहेत, तर एकूण विद्यार्थ्यांची संख्या किती?", listOf("A) 20", "B) 30", "C) 10", "D) 25"), 0),
            Question(49, "प्रश्न ४९: एका कुटुंबात 6 सदस्य आहेत. त्यापैकी 4 सदस्य महिला आहेत. पुरुषांची संख्या किती आहे?", listOf("A) 2", "B) 4", "C) 6", "D) 3"), 0),
            Question(50, "प्रश्न ५०: एका दुकानात 30 चॉकलेट आहेत. त्यापैकी 10 चॉकलेट विकल्या गेल्या. शिल्लक चॉकलेटची संख्या किती आहे?", listOf("A) 20", "B) 30", "C) 10", "D) 15"), 0)
        )
    }

}
