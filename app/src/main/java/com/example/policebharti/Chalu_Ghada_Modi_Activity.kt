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

class Chalu_Ghada_Modi_Activity : AppCompatActivity() {


    private var currentQuestionIndex = 0
    private var score = 0
    private lateinit var questionList: List<Question>
    private lateinit var back: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chalu_ghada_modi)
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
                Toast.makeText(this, "Correct! Answer: $correctAnswerText", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Incorrect! Correct Answer: $correctAnswerText", Toast.LENGTH_SHORT).show()
            }

            currentQuestionIndex++

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
            Question(1, "प्रश्न १: २०२४ मध्ये कोणत्या शहरात G20 शिखर परिषद झाली?", listOf("A) मुंबई", "B) दिल्ली", "C) कोलकाता", "D) बेंगळुरू"), 1),
            Question(2, "प्रश्न २: २०२४ मध्ये भारतीय अंतराळ संस्था (ISRO) ने कोणता मिशन यशस्वीरीत्या पूर्ण केला?", listOf("A) मंगळयान", "B) चंद्रयान-4", "C) आदित्य-L1", "D) गगनयान"), 2),
            Question(3, "प्रश्न ३: २०२४ च्या ऑलिंपिकमध्ये भारताने किती पदकं जिंकली?", listOf("A) ५", "B) ७", "C) ८", "D) १०"), 2),
            Question(4, "प्रश्न ४: २०२४ मध्ये कोणता राज्य पर्यावरणीय विधेयक लागू करण्यासाठी प्रसिद्धीमध्ये होता?", listOf("A) महाराष्ट्र", "B) उत्तर प्रदेश", "C) केरळ", "D) राजस्थान"), 0),
            Question(5, "प्रश्न ५: २०२४ मध्ये कोणत्या खेळाडूने जागतिक बॅडमिंटन चॅम्पियनशिप जिंकली?", listOf("A) पी.व्ही. सिंधू", "B) किदांबी श्रीकांत", "C) लक्ष्य सेन", "D) साईना नेहवाल"), 2),
            Question(6, "प्रश्न ६: २०२४ मध्ये भारतात कोणत्या क्षेत्रात आर्थिक वाढ झाली आहे?", listOf("A) माहिती तंत्रज्ञान", "B) शेती", "C) बांधकाम", "D) उत्पादन"), 0),
            Question(7, "प्रश्न ७: २०२४ मध्ये भारतात कोणता मोठा पर्यावरणीय महोत्सव साजरा झाला?", listOf("A) वन महोत्सव", "B) गंगा महोत्सव", "C) कावेरी महोत्सव", "D) यमुना महोत्सव"), 1),
            Question(8, "प्रश्न ८: २०२४ च्या भारतीय गणराज्य दिवसाच्या परेडमध्ये कोणता देश प्रमुख पाहुणा होता?", listOf("A) अमेरिका", "B) फ्रान्स", "C) रशिया", "D) जपान"), 3),
            Question(9, "प्रश्न ९: २०२४ मध्ये भारताने कोणत्या क्षेत्रात विक्रमी निर्यात केली?", listOf("A) औषध", "B) कृषी", "C) इलेक्ट्रॉनिक्स", "D) वस्त्र"), 1),
            Question(10, "प्रश्न १०: २०२४ मध्ये कोणत्या राज्याने नवीन औद्योगिक धोरण जाहीर केले?", listOf("A) गुजरात", "B) कर्नाटक", "C) महाराष्ट्र", "D) तामिळनाडू"), 2),
            Question(11, "प्रश्न ११: २०२४ मध्ये कोणत्या देशाने भारतीय पर्यटकांसाठी व्हिसा मोफत केला?", listOf("A) जपान", "B) दक्षिण कोरिया", "C) मलेशिया", "D) सिंगापूर"), 3),
            Question(12, "प्रश्न १२: २०२४ मध्ये कोणत्या राज्यात प्रथम 'इलेक्ट्रिक बाईक' सेवा सुरू करण्यात आली?", listOf("A) गोवा", "B) महाराष्ट्र", "C) दिल्ली", "D) तामिळनाडू"), 0),
            Question(13, "प्रश्न १३: २०२४ मध्ये कोणत्या भारतीय चित्रपटाने सर्वोत्कृष्ट आंतरराष्ट्रीय चित्रपटाचा पुरस्कार जिंकला?", listOf("A) पठाण", "B) RRR", "C) गदर 2", "D) जवानी जानेमन"), 1),
            Question(14, "प्रश्न १४: २०२४ मध्ये कोणत्या भारतीय शहरात 'स्मार्ट सिटी मिशन' पुरस्कार जिंकला?", listOf("A) इंदौर", "B) पुणे", "C) सूरत", "D) जयपूर"), 0),
            Question(15, "प्रश्न १५: २०२४ मध्ये जागतिक पर्यावरण दिनाचा मुख्य थीम काय होता?", listOf("A) प्लास्टिक प्रदूषण", "B) हवामान बदल", "C) जैवविविधता", "D) वनसंवर्धन"), 0),
            Question(16, "प्रश्न १६: २०२४ मध्ये कोणत्या भारतीय लेखकाला साहित्य नोबेल पुरस्कार मिळाला?", listOf("A) चेतन भगत", "B) अरुंधती रॉय", "C) झुंपा लाहिरी", "D) सलमान रश्दी"), 1),
            Question(17, "प्रश्न १७: २०२४ मध्ये जागतिक आरोग्य संघटनेने कोणत्या आरोग्य समस्येवर नवीन लस जाहीर केली?", listOf("A) मलेरिया", "B) हिवताप", "C) कोरोना", "D) इन्फ्लुएंझा"), 0),
            Question(18, "प्रश्न १८: २०२४ मध्ये भारतात 'स्टार्टअप इंडिया' योजनेत कोणत्या क्षेत्राला प्रोत्साहन देण्यात आले?", listOf("A) कृषी", "B) तंत्रज्ञान", "C) ऊर्जा", "D) फिनटेक"), 2),
            Question(19, "प्रश्न १९: २०२४ मध्ये भारतीय क्रिकेट संघाचा प्रशिक्षक कोण होता?", listOf("A) राहुल द्रविड", "B) सौरव गांगुली", "C) अनिल कुंबळे", "D) महेंद्रसिंग धोनी"), 0),
            Question(20, "प्रश्न २०: २०२४ मध्ये भारतात कोणत्या शहरात पहिले 'वायू प्रदूषण नियंत्रण' सेंटर स्थापन झाले?", listOf("A) दिल्ली", "B) लखनऊ", "C) मुंबई", "D) पटना"), 0),
            Question(21, "प्रश्न २१: २०२४ मध्ये महाराष्ट्राचे राज्यपाल कोण होते?", listOf("A) भगतसिंग कोश्यारी", "B) रमेश बैस", "C) विद्यासागर राव", "D) शंकरराव चव्हाण"), 1),
            Question(22, "प्रश्न २२: २०२४ मध्ये कोणत्या व्यक्तीने महाराष्ट्राचे मुख्यमंत्रीपद भूषविले?", listOf("A) उद्धव ठाकरे", "B) देवेंद्र फडणवीस", "C) एकनाथ शिंदे", "D) अजित पवार"), 2),
            Question(23, "प्रश्न २३: २०२४ च्या महाराष्ट्राच्या अर्थसंकल्पात शेतकरी कल्याणासाठी किती रक्कम मंजूर करण्यात आली?", listOf("A) ₹३०,००० कोटी", "B) ₹५०,००० कोटी", "C) ₹२०,००० कोटी", "D) ₹४०,००० कोटी"), 1),
            Question(24, "प्रश्न २४: महाराष्ट्रात २०२४ मध्ये कोणता नवीन पर्यावरणीय प्रकल्प सुरू करण्यात आला?", listOf("A) सोलार पार्क", "B) वन संवर्धन अभियान", "C) ग्रीन एनर्जी मिशन", "D) वनीकरण योजना"), 3),
            Question(25, "प्रश्न २५: २०२४ मध्ये महाराष्ट्रातील कोणत्या शहरात जागतिक व्यापार मेळावा आयोजित करण्यात आला?", listOf("A) पुणे", "B) नागपूर", "C) औरंगाबाद", "D) मुंबई"), 3),
            Question(26, "प्रश्न २६: महाराष्ट्राने २०२४ मध्ये कोणता नवीन औद्योगिक धोरण राबवले?", listOf("A) इलेक्ट्रॉनिक्स उत्पादन धोरण", "B) स्टार्टअप धोरण", "C) हरित औद्योगिक धोरण", "D) पर्यटन विकास धोरण"), 1),
            Question(27, "प्रश्न २७: २०२४ मध्ये महाराष्ट्र सरकारने कोणत्या क्षेत्रात डिजिटल क्रांती आणली?", listOf("A) आरोग्य", "B) शिक्षण", "C) कृषी", "D) उद्योग"), 0),
            Question(28, "प्रश्न २८: २०२४ मध्ये महाराष्ट्र सरकारने कोणत्या महानगरात नवीन मेट्रो लाईन सुरू केली?", listOf("A) मुंबई", "B) पुणे", "C) नागपूर", "D) नाशिक"), 2),
            Question(29, "प्रश्न २९: २०२४ मध्ये महाराष्ट्राने कोणत्या क्षेत्रासाठी जागतिक गुंतवणूकदार परिषद आयोजित केली?", listOf("A) माहिती तंत्रज्ञान", "B) कृषी", "C) वाहन उद्योग", "D) शैक्षणिक क्षेत्र"), 3),
            Question(30, "प्रश्न ३०: २०२४ मध्ये महाराष्ट्राच्या कोणत्या शहराने स्मार्ट सिटी स्पर्धेत प्रथम क्रमांक पटकावला?", listOf("A) ठाणे", "B) औरंगाबाद", "C) नाशिक", "D) सोलापूर"), 0),
            Question(31, "प्रश्न ३१: २०२४ मध्ये महाराष्ट्रात कोणत्या कार्यक्रमाने महिलांच्या सुरक्षेसाठी विशेष योजना राबवली?", listOf("A) महिला सशक्तीकरण योजना", "B) नारी सुरक्षा अभियान", "C) सखी केंद्र योजना", "D) सुरक्षितता योजने"), 2),
            Question(32, "प्रश्न ३२: २०२४ मध्ये महाराष्ट्रात कोणत्या विद्यापीठाने पहिली आंतरराष्ट्रीय शैक्षणिक परिषद आयोजित केली?", listOf("A) मुंबई विद्यापीठ", "B) सावित्रीबाई फुले पुणे विद्यापीठ", "C) नागपूर विद्यापीठ", "D) औरंगाबाद विद्यापीठ"), 1),
            Question(33, "प्रश्न ३३: महाराष्ट्रातील कोणत्या शहराने २०२४ मध्ये स्वच्छता स्पर्धेत प्रथम क्रमांक मिळवला?", listOf("A) पुणे", "B) नाशिक", "C) मुंबई", "D) नागपूर"), 0),
            Question(34, "प्रश्न ३४: महाराष्ट्रात २०२४ मध्ये कृषी क्षेत्रात कोणता नवीन प्रकल्प सुरू करण्यात आला?", listOf("A) जैविक शेती प्रकल्प", "B) कृषी विकास मिशन", "C) हरित शेती प्रकल्प", "D) समृद्ध कृषी योजना"), 2),
            Question(35, "प्रश्न ३५: २०२४ मध्ये महाराष्ट्रात कोणत्या सामाजिक अभियानाने बालविवाह विरोधात कार्य केले?", listOf("A) साक्षरता मोहीम", "B) बालविवाह बंदी अभियान", "C) बालसुरक्षा योजना", "D) लाडली लक्ष्मी योजना"), 1),
            Question(36, "प्रश्न ३६: २०२४ मध्ये महाराष्ट्राने कोणत्या नवीन बांधकाम प्रकल्पाची सुरुवात केली?", listOf("A) मुंबई मेट्रो विस्तार", "B) पुणे बुलेट ट्रेन प्रकल्प", "C) नाशिक इंडस्ट्रियल हब", "D) औरंगाबाद इन्फ्रास्ट्रक्चर प्रकल्प"), 0),
            Question(37, "प्रश्न ३७: महाराष्ट्रातील कोणत्या जिल्ह्यात २०२४ मध्ये सर्वाधिक शेती उत्पन्न झाले?", listOf("A) सांगली", "B) सोलापूर", "C) नाशिक", "D) सातारा"), 2),
            Question(38, "प्रश्न ३८: २०२४ मध्ये महाराष्ट्रात कोणत्या जिल्ह्याने स्मार्ट कृषि तंत्रज्ञानात प्रगती साधली?", listOf("A) नाशिक", "B) कोल्हापूर", "C) पुणे", "D) औरंगाबाद"), 0),
            Question(39, "प्रश्न ३९: २०२४ मध्ये महाराष्ट्रातील कोणत्या महापुरुषाचा जयंती सोहळा राष्ट्रीय पातळीवर साजरा करण्यात आला?", listOf("A) शिवाजी महाराज", "B) बाबासाहेब आंबेडकर", "C) महात्मा फुले", "D) लोकमान्य टिळक"), 1),
            Question(40, "प्रश्न ४०: २०२४ मध्ये महाराष्ट्रात कोणत्या शहरात 'रान फुलांची उत्सव' आयोजित केली गेली?", listOf("A) सातारा", "B) कोल्हापूर", "C) पुणे", "D) रत्नागिरी"), 0),
            Question(41, "प्रश्न ४१: महाराष्ट्राच्या कोणत्या जिल्ह्यात २०२४ मध्ये सर्वाधिक औद्योगिक गुंतवणूक झाली?", listOf("A) मुंबई", "B) पुणे", "C) नाशिक", "D) औरंगाबाद"), 1),
            Question(42, "प्रश्न ४२: महाराष्ट्रात २०२४ मध्ये कोणत्या शिक्षण संस्थेने नवा तंत्रज्ञान केंद्र उघडला?", listOf("A) IIT मुंबई", "B) सावित्रीबाई फुले पुणे विद्यापीठ", "C) COEP पुणे", "D) VJTI मुंबई"), 2),
            Question(43, "प्रश्न ४३: २०२४ मध्ये महाराष्ट्रातील कोणत्या प्रकल्पाने पर्यावरण संरक्षणाच्या दृष्टीने महत्त्वाची भूमिका बजावली?", listOf("A) सह्याद्री संरक्षण प्रकल्प", "B) ताम्हिणी अभयारण्य योजना", "C) नाशिक हरित शहर योजना", "D) पश्चिम घाट संरक्षण"), 3),
            Question(44, "प्रश्न ४४: २०२४ मध्ये महाराष्ट्रातील कोणत्या शहरात पहिल्या हायड्रोजन बसची सेवा सुरू झाली?", listOf("A) मुंबई", "B) पुणे", "C) नागपूर", "D) ठाणे"), 1),
            Question(45, "प्रश्न ४५: महाराष्ट्रात २०२४ मध्ये कोणत्या नवीन राष्ट्रीय महामार्गाची सुरुवात झाली?", listOf("A) मुंबई-गोवा महामार्ग", "B) पुणे-बंगळुरू महामार्ग", "C) औरंगाबाद-नाशिक महामार्ग", "D) नागपूर-हैदराबाद महामार्ग"), 0),
            Question(46, "प्रश्न ४६: २०२४ मध्ये महाराष्ट्र सरकारने कोणत्या कार्यक्रमाअंतर्गत महिलांना ५०% आरक्षण मंजूर केले?", listOf("A) स्थानिक स्वराज्य संस्था निवडणूक", "B) शालेय व्यवस्थापन", "C) महिला विकास मंडळ", "D) आरोग्य संस्था"), 0),
            Question(47, "प्रश्न ४७: महाराष्ट्रातील कोणत्या शहरात २०२४ मध्ये सर्वाधिक पर्जन्यमानाची नोंद झाली?", listOf("A) रत्नागिरी", "B) नाशिक", "C) पुणे", "D) नागपूर"), 0),
            Question(48, "प्रश्न ४८: २०२४ मध्ये महाराष्ट्र सरकारने कोणत्या शहरात नवीन आयटी पार्क स्थापन केले?", listOf("A) औरंगाबाद", "B) पुणे", "C) नाशिक", "D) नागपूर"), 3),
            Question(49, "प्रश्न ४९: महाराष्ट्रात २०२४ मध्ये कोणत्या पर्यटन स्थळाला UNESCO जागतिक वारसा स्थळ म्हणून घोषित करण्यात आले?", listOf("A) अजिंठा लेणी", "B) वेरूळ लेणी", "C) गेटवे ऑफ इंडिया", "D) पंढरपूर मंदिर"), 1),
            Question(50, "प्रश्न ५०: महाराष्ट्रातील कोणत्या खेळाडूने २०२४ मध्ये राष्ट्रीय क्रीडा स्पर्धेत सुवर्णपदक जिंकले?", listOf("A) नीरज चोपडा", "B) विजेंदर सिंग", "C) हिमा दास", "D) स्मृती मंधाना"), 3)
        )
    }

}