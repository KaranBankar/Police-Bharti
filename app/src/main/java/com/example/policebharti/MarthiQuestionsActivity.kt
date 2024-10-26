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

class MarthiQuestionsActivity : AppCompatActivity() {

    private var currentQuestionIndex = 0
    private var score = 0
    private lateinit var questionList: List<Question>
    private lateinit var back: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_marthi_questions)
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
                Toast.makeText(this, "Incorrect! Correct Answer: $correctAnswerText", Toast.LENGTH_SHORT).show()
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
            Question(1, "प्रश्न १: 'गड' हा कोणत्या शब्दाचा एकवचन आहे?", listOf("A) गडांचे", "B) गड", "C) गडां", "D) गडांनी"), 1),
            Question(2, "प्रश्न २: 'सूर्य' हा कोणता शब्दप्रकार आहे?", listOf("A) नाम", "B) विशेषण", "C) क्रियापद", "D) सर्वनाम"), 0),
            Question(3, "प्रश्न ३: 'सुखी' शब्दाचा विरुद्धार्थी शब्द कोणता?", listOf("A) दुःखी", "B) आनंदी", "C) शांत", "D) उदास"), 0),
            Question(4, "प्रश्न ४: 'पाणी' या शब्दाचे अनेकवचन काय आहे?", listOf("A) पाण्यांचे", "B) पाणी", "C) पाणे", "D) पाण्या"), 2),
            Question(5, "प्रश्न ५: 'चंद्र' या शब्दाचा अर्थ काय आहे?", listOf("A) रात्रीचा तारा", "B) सूर्याचा शिशु", "C) चंद्रमा", "D) ग्रह"), 2),
            Question(6, "प्रश्न ६: 'गाडी' या शब्दात कितव्या प्रकारचे स्वर आहे?", listOf("A) एक", "B) दोन", "C) तीन", "D) चार"), 1),
            Question(7, "प्रश्न ७: 'पुस्तक' या शब्दातील 'पुस्त' हा कोणता शब्दप्रकार आहे?", listOf("A) नाम", "B) विशेषण", "C) क्रियापद", "D) सर्वनाम"), 0),
            Question(8, "प्रश्न ८: 'शाळा' या शब्दाचा भूतकाळ काय आहे?", listOf("A) शाळा होते", "B) शाळा झाली", "C) शाळा होत होती", "D) शाळा होती"), 3),
            Question(9, "प्रश्न ९: 'फुल' हा शब्द कोणत्या प्रकारात येतो?", listOf("A) नाम", "B) विशेषण", "C) क्रियापद", "D) सर्वनाम"), 0),
            Question(10, "प्रश्न १०: 'किताब' हा कोणता शब्दप्रकार आहे?", listOf("A) विशेषण", "B) क्रियापद", "C) नाम", "D) सर्वनाम"), 2),
            Question(11, "प्रश्न ११: 'घड्याळ' या शब्दाचा अर्थ काय आहे?", listOf("A) कालमापन यंत्र", "B) संगीत यंत्र", "C) छाया यंत्र", "D) चित्र यंत्र"), 0),
            Question(12, "प्रश्न १२: 'काम' या शब्दाचा अनेकवचन रूप काय आहे?", listOf("A) कामां", "B) कामे", "C) कामाचे", "D) काम"), 1),
            Question(13, "प्रश्न १३: 'शांत' या शब्दाचे समानार्थी शब्द कोणते आहेत?", listOf("A) स्थिर", "B) शांती", "C) शांतता", "D) सर्व"), 3),
            Question(14, "प्रश्न १४: 'टपकणे' या क्रियापदाचा भूतकाळ काय आहे?", listOf("A) टपकला", "B) टपकला होता", "C) टपकले", "D) टपकले होते"), 2),
            Question(15, "प्रश्न १५: 'पुस्तक' या शब्दाचा अर्थ काय आहे?", listOf("A) ज्ञानाचा स्रोत", "B) चित्र", "C) शाळा", "D) शाळेचा मित्र"), 0),
            Question(16, "प्रश्न १६: 'वडील' या शब्दातील 'वड' कोणत्या प्रकारात येतो?", listOf("A) नाम", "B) विशेषण", "C) क्रियापद", "D) सर्वनाम"), 0),
            Question(17, "प्रश्न १७: 'कडवट' या शब्दाचा विरुद्धार्थी शब्द कोणता?", listOf("A) गोड", "B) तिखट", "C) मिठ", "D) थंड"), 0),
            Question(18, "प्रश्न १८: 'पाखरू' या शब्दाचा अर्थ काय आहे?", listOf("A) पक्ष्याचे बाळ", "B) एक पक्षी", "C) पंख असलेला प्राणी", "D) सर्व"), 3),
            Question(19, "प्रश्न १९: 'दूरदर्शन' या शब्दाचा अर्थ काय आहे?", listOf("A) दूरचा दृश्य", "B) चित्रपट", "C) टेलिव्हिजन", "D) सर्व"), 2),
            Question(20, "प्रश्न २०: 'खेळ' या शब्दाचा अनेकवचन रूप काय आहे?", listOf("A) खेळांचे", "B) खेळ", "C) खेळा", "D) खेळे"), 1),
            Question(21, "प्रश्न २१: 'पाऊस' या शब्दाचा भूतकाळ काय आहे?", listOf("A) पाऊस पडला", "B) पाऊस पडला होता", "C) पाऊस पडलं", "D) पाऊस पडत होता"), 0),
            Question(22, "प्रश्न २२: 'गाडी' या शब्दाचा विरुद्धार्थी शब्द कोणता?", listOf("A) पादचारी", "B) शाळा", "C) चालणे", "D) वाकणे"), 0),
            Question(23, "प्रश्न २३: 'वृत्त' या शब्दाचा अर्थ काय आहे?", listOf("A) बातमी", "B) छायाचित्र", "C) चित्र", "D) विद्या"), 0),
            Question(24, "प्रश्न २४: 'शांतता' या शब्दाचा भूतकाळ काय आहे?", listOf("A) शांत होता", "B) शांत होता होता", "C) शांत होते", "D) शांत होईल"), 0),
            Question(25, "प्रश्न २५: 'उंच' या शब्दाचा अर्थ काय आहे?", listOf("A) कमी", "B) वाढ", "C) उंची", "D) जलद"), 2),
            Question(26, "प्रश्न २६: 'गडबड' या शब्दाचा विरुद्धार्थी शब्द कोणता?", listOf("A) शांती", "B) शांत", "C) स्थिर", "D) शांतता"), 0),
            Question(27, "प्रश्न २७: 'संगीत' या शब्दाचा अर्थ काय आहे?", listOf("A) नृत्य", "B) कला", "C) आवाज", "D) ध्वनी"), 2),
            Question(28, "प्रश्न २८: 'वाट' या शब्दाचा अनेकवचन रूप काय आहे?", listOf("A) वाटा", "B) वाटा", "C) वाटांचे", "D) वाटा"), 1),
            Question(29, "प्रश्न २९: 'नवीन' हा शब्द कोणत्या प्रकारात येतो?", listOf("A) नाम", "B) विशेषण", "C) क्रियापद", "D) सर्वनाम"), 1),
            Question(30, "प्रश्न ३०: 'संविधान' या शब्दाचा अर्थ काय आहे?", listOf("A) कायदा", "B) नियम", "C) संविधान", "D) न्याय"), 0),
            Question(31, "प्रश्न ३१: 'खाणे' या क्रियापदाचा भूतकाळ काय आहे?", listOf("A) खाल्ले", "B) खात होता", "C) खात", "D) खाणार"), 0),
            Question(32, "प्रश्न ३२: 'दिसा' या शब्दाचा अनेकवचन रूप काय आहे?", listOf("A) दिवसा", "B) दिवस", "C) दिवशी", "D) दिवसांचे"), 1),
            Question(33, "प्रश्न ३३: 'प्यायला' या शब्दाचा वर्तमान काळ काय आहे?", listOf("A) पितो", "B) पिला", "C) पिऊन", "D) पिलं"), 0),
            Question(34, "प्रश्न ३४: 'असणे' या शब्दाचा भूतकाळ काय आहे?", listOf("A) आहे", "B) होते", "C) होत होता", "D) होता"), 3),
            Question(35, "प्रश्न ३५: 'कशासाठी' हा प्रश्नवाचक शब्द कोणत्या प्रकारात येतो?", listOf("A) विशेषण", "B) सर्वनाम", "C) क्रियापद", "D) विशेषक"), 1),
            Question(36, "प्रश्न ३६: 'शुभ' या शब्दाचा विरुद्धार्थी शब्द कोणता?", listOf("A) अशुभ", "B) चांगला", "C) बुरा", "D) कमी"), 0),
            Question(37, "प्रश्न ३७: 'निसर्ग' या शब्दाचा अर्थ काय आहे?", listOf("A) पर्यावरण", "B) जंगल", "C) वनस्पती", "D) सर्व"), 3),
            Question(38, "प्रश्न ३८: 'पद' या शब्दाचा अनेकवचन रूप काय आहे?", listOf("A) पदे", "B) पदांचे", "C) पदां", "D) पदे"), 0),
            Question(39, "प्रश्न ३९: 'उद्यान' या शब्दाचा अर्थ काय आहे?", listOf("A) बाग", "B) बागायती", "C) वन", "D) झाडी"), 0),
            Question(40, "प्रश्न ४०: 'ज्ञान' या शब्दाचा भूतकाळ काय आहे?", listOf("A) ज्ञानी", "B) ज्ञानी होता", "C) ज्ञान होत होता", "D) ज्ञान होत"), 1),
            Question(41, "प्रश्न ४१: 'आकाश' या शब्दाचा अर्थ काय आहे?", listOf("A) निसर्ग", "B) ग्रह", "C) वायू", "D) आभाळ"), 3),
            Question(42, "प्रश्न ४२: 'डोंगर' या शब्दाचा अनेकवचन रूप काय आहे?", listOf("A) डोंगरांचे", "B) डोंगर", "C) डोंगरां", "D) डोंगरां"), 0),
            Question(43, "प्रश्न ४३: 'भाषा' या शब्दाचा अर्थ काय आहे?", listOf("A) संवाद साधण्याचा माध्यम", "B) लेखन", "C) बोलणे", "D) सर्व"), 0),
            Question(44, "प्रश्न ४४: 'रंग' या शब्दाचा अनेकवचन रूप काय आहे?", listOf("A) रंगांचे", "B) रंग", "C) रंगा", "D) रंगा"), 0),
            Question(45, "प्रश्न ४५: 'राजा' या शब्दाचा अर्थ काय आहे?", listOf("A) शासक", "B) ध्वज", "C) रण", "D) चित्र"), 0),
            Question(46, "प्रश्न ४६: 'संपूर्ण' या शब्दाचा भूतकाळ काय आहे?", listOf("A) संपूर्ण झाले", "B) संपूर्ण होत", "C) संपूर्ण झाले होते", "D) संपूर्ण होता"), 2),
            Question(47, "प्रश्न ४७: 'विविधता' या शब्दाचा अर्थ काय आहे?", listOf("A) एकरूपता", "B) विविधता", "C) बौद्धिकता", "D) सर्व"), 1),
            Question(48, "प्रश्न ४८: 'उच्च' या शब्दाचा विरुद्धार्थी शब्द कोणता?", listOf("A) कमी", "B) थोडा", "C) अधो", "D) चालवणे"), 0),
            Question(49, "प्रश्न ४९: 'शिक्षण' या शब्दाचा अर्थ काय आहे?", listOf("A) ज्ञान मिळवणे", "B) शाळा", "C) विद्याशाखा", "D) सर्व"), 0),
            Question(50, "प्रश्न ५०: 'विज्ञान' या शब्दाचा अर्थ काय आहे?", listOf("A) माहिती तंत्रज्ञान", "B) ज्ञान", "C) प्राकृतिक विज्ञान", "D) सर्व"), 3)
        )
    }

}
