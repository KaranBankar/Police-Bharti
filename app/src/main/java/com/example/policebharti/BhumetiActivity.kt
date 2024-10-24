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

class BhumetiActivity : AppCompatActivity() {

    private var currentQuestionIndex = 0
    private var score = 0
    private lateinit var questionList: List<Question>
    private lateinit var back: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bhumeti)
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

            Question(1, "प्रश्न १: पृथ्वीच्या आतील कोणत्या थराला 'ज्वालामुखीजन्य पदार्थांचा थर' असे संबोधले जाते?", listOf("A) क्रस्ट", "B) मॅन्टल", "C) कोअर", "D) लिथोस्फीअर"), 1),
            Question(2, "प्रश्न २: कोणत्या अक्षांशावर 'महासागरातील मृत सागर' स्थित आहे?", listOf("A) 30° उत्तर", "B) 50° उत्तर", "C) 60° दक्षिण", "D) 30° दक्षिण"), 0),
            Question(3, "प्रश्न ३: जगात सर्वाधिक विषम हवामान असलेला खंड कोणता आहे?", listOf("A) आशिया", "B) आफ्रिका", "C) अंटार्क्टिका", "D) उत्तर अमेरिका"), 2),
            Question(4, "प्रश्न ४: वेस्टर्डिज वारे कोणत्या अक्षांश दरम्यान वाहतात?", listOf("A) 30° आणि 60° उत्तर व दक्षिण", "B) 0° आणि 30° उत्तर", "C) 60° आणि 90° दक्षिण", "D) 90° उत्तर"), 0),
            Question(5, "प्रश्न ५: 'प्लेट टेक्टोनिक्स' सिद्धांतानुसार भारत कोणत्या तक्त्यावर स्थित आहे?", listOf("A) यूरेशियन प्लेट", "B) अफ्रिकन प्लेट", "C) इंडो-ऑस्ट्रेलियन प्लेट", "D) अंटार्क्टिक प्लेट"), 2),
            Question(6, "प्रश्न ६: कोटोपॅक्सी ज्वालामुखी कोणत्या देशात स्थित आहे?", listOf("A) पेरू", "B) इटली", "C) इक्वाडोर", "D) चिली"), 2),
            Question(7, "प्रश्न ७: 'स्नो लाइन' ही संज्ञा कोणत्या उंचीवरील बर्फाच्या कायमस्वरूपी स्थितीशी संबंधित आहे?", listOf("A) समुद्रसपाटीपासून 4000 मीटर", "B) समुद्रसपाटीपासून 5000 मीटर", "C) समुद्रसपाटीपासून 6000 मीटर", "D) समुद्रसपाटीपासून 7000 मीटर"), 1),
            Question(8, "प्रश्न ८: हिमालय पर्वत कशामुळे तयार झाला आहे?", listOf("A) ज्वालामुखी क्रिया", "B) क्षेपण", "C) समुद्राच्या खालील प्लेट्सची टक्कर", "D) नदीच्या अभिसरण क्रिया"), 2),
            Question(9, "प्रश्न ९: कोणत्या प्रकारच्या नदीखोऱ्याला 'V आकाराचे खोरे' म्हणतात?", listOf("A) लहान व जमिनीतील नदी", "B) युवा अवस्था असलेली नदी", "C) वृद्ध अवस्था असलेली नदी", "D) विकसित नदी"), 1),
            Question(10, "प्रश्न १०: सहारा वाळवंट कशामुळे बनले आहे?", listOf("A) विषम हवामान", "B) प्राचीन वाळवंटांची प्रक्रिया", "C) पर्वत निर्माण क्रिया", "D) समुद्री वारे"), 0),
            Question(11, "प्रश्न ११: ग्रीनलँडचे प्रमुख हवामान कोणते आहे?", listOf("A) उष्णकटिबंधीय", "B) समशीतोष्ण", "C) ध्रुवीय", "D) भूमध्य"), 2),
            Question(12, "प्रश्न १२: १४° पूर्व रेखांशावर कोणता महासागर आहे?", listOf("A) अटलांटिक महासागर", "B) प्रशांत महासागर", "C) भारतीय महासागर", "D) आर्क्टिक महासागर"), 0),
            Question(13, "प्रश्न १३: पृथ्वीच्या कोणत्या घटकाला 'पॅलिओलिथिक क्रस्ट' म्हणतात?", listOf("A) बाह्य मॅन्टल", "B) क्रस्टचा वरचा भाग", "C) अंतर्गत कोअर", "D) समुद्रतळाची प्लेट"), 1),
            Question(14, "प्रश्न १४: वायुमंडलीय दाबाचे परिमाण मोजण्यासाठी कोणते उपकरण वापरले जाते?", listOf("A) हायड्रोमीटर", "B) बारोमीटर", "C) अॅनेमोमीटर", "D) हायग्रोमीटर"), 1),
            Question(15, "प्रश्न १५: 'एल निनो' घटनेचा परिणाम कोणत्या महासागरात जास्त प्रमाणात दिसून येतो?", listOf("A) प्रशांत महासागर", "B) अटलांटिक महासागर", "C) भारतीय महासागर", "D) आर्क्टिक महासागर"), 0),
            Question(16, "प्रश्न १६: 'ज्वालामुखीची क्रिया' कोणत्या प्रकारच्या पृथ्वीच्या क्रियाकलापाशी संबंधित आहे?", listOf("A) पृथ्वीची बाह्यरेखा", "B) मॅन्टल क्रिया", "C) क्षेपण क्रिया", "D) पृष्ठभाग बदल"), 1),
            Question(17, "प्रश्न १७: 'ट्रेड विंड्स' कोणत्या प्रदेशात सापडतात?", listOf("A) उष्णकटिबंधीय", "B) उत्तर ध्रुवीय", "C) दक्षिण ध्रुवीय", "D) समशीतोष्ण"), 0),
            Question(18, "प्रश्न १८: भारतात भू-गर्भात कोणत्या प्रकारचे खनिज साठे जास्त प्रमाणात सापडतात?", listOf("A) लोखंड", "B) तांबे", "C) यूरेनियम", "D) कोळसा"), 3),
            Question(19, "प्रश्न १९: 'किंबरलाइट' खनिज कोणत्या खनिजाचे स्रोत आहे?", listOf("A) हीरा", "B) पन्ना", "C) निळा नीलम", "D) पन्ना"), 0),
            Question(20, "प्रश्न २०: कोणत्या पर्वतरांगेतून नाईल नदीचा उगम होतो?", listOf("A) रिफ्ट व्हॅली", "B) अटलास पर्वत", "C) ईस्ट आफ्रिकन हायलँड", "D) अँडीज पर्वत"), 2),
            // Bhumati (Maharashtra) 21 to 50
            Question(21, "प्रश्न २१: महाराष्ट्रातील सर्वात उंच शिखर कोणते आहे?", listOf("A) कुमारी शिखर", "B) हरिश्चंद्रगड", "C) कंचनगड", "D) अजंठा"), 1),
            Question(22, "प्रश्न २२: 'कृष्णा' नदीचा उगम कोणत्या पर्वतापासून होतो?", listOf("A) सह्याद्री", "B) नीलगिरी", "C) विंध्य पर्वत", "D) कचन्ना"), 0),
            Question(23, "प्रश्न २३: महाराष्ट्रात कोणत्या ठिकाणी 'कुशालनगर' आहे?", listOf("A) नाशिक", "B) सोलापूर", "C) पुणे", "D) ठाणे"), 3),
            Question(24, "प्रश्न २४: पुणे जिल्ह्यातील 'अंबेगाव' कशासाठी प्रसिद्ध आहे?", listOf("A) शैक्षणिक संस्था", "B) किल्ले", "C) धरणे", "D) औद्योगिक विकास"), 1),
            Question(25, "प्रश्न २५: 'कोकण' क्षेत्रातील प्रमुख पिक कोणते आहे?", listOf("A) भाजीपाला", "B) कापूस", "C) तांदूळ", "D) आंबा"), 3),
            Question(26, "प्रश्न २६: महाराष्ट्रातील सर्वात मोठे धरण कोणते आहे?", listOf("A) भीमा धरण", "B) कर्णाळा धरण", "C) सुळखांब धरण", "D) भंडारधार धरण"), 0),
            Question(27, "प्रश्न २७: 'आसना' पाणलोट क्षेत्र कोणत्या नदीसाठी आहे?", listOf("A) गोदावरी", "B) भिमा", "C) कृष्णा", "D) ताप्ती"), 2),
            Question(28, "प्रश्न २८: महाराष्ट्रात 'पंचगणी' या ठिकाणी कोणत्या प्रकारचा हवामान आहे?", listOf("A) उष्णकटिबंधीय", "B) समशीतोष्ण", "C) ध्रुवीय", "D) समुद्र किनारी"), 1),
            Question(29, "प्रश्न २९: 'कोल्हापूर' जिल्ह्यातील प्रमुख साखरेचा कारखाना कोणता आहे?", listOf("A) माळशिरस", "B) सासवड", "C) बारामती", "D) खंडाळा"), 1),
            Question(30, "प्रश्न ३०: 'साताऱ्याच्या' परंपरागत पिकामध्ये कोणते पिक महत्वाचे आहे?", listOf("A) गहू", "B) ज्वारी", "C) तांदूळ", "D) भाजीपाला"), 1),
            Question(31, "प्रश्न ३१: 'भीमा' नदीवर कोणते प्रमुख धरण आहे?", listOf("A) भीमा धरण", "B) परळी धरण", "C) मलट बंधारा", "D) कर्णाळा धरण"), 0),
            Question(32, "प्रश्न ३२: कोणत्या नदीवर 'कर्जत' शहर आहे?", listOf("A) उल्हास", "B) भोर", "C) शिरपूर", "D) भीमा"), 0),
            Question(33, "प्रश्न ३३: 'गडचिरोली' जिल्ह्यात कोणत्या प्रमुख वन्यजीव प्राण्याचे अस्तित्व आहे?", listOf("A) सिंह", "B) बिबट्या", "C) वाघ", "D) भेकर"), 2),
            Question(34, "प्रश्न ३४: 'नासिक' शहरातील प्रमुख धार्मिक स्थळ कोणते आहे?", listOf("A) त्र्यंबकेश्वर", "B) सिद्धिविनायक", "C) पंचवटी", "D) गडाची किल्ला"), 0),
            Question(35, "प्रश्न ३५: 'शिवाजी' जलदुर्ग कोणत्या ठिकाणी आहे?", listOf("A) रायगड", "B) पुणे", "C) सिंधुदुर्ग", "D) ठाणे"), 2),
            Question(36, "प्रश्न ३६: 'कोल्हापूर' येथे कोणत्या प्राचीन मंदिराचे महत्व आहे?", listOf("A) महालक्ष्मी मंदिर", "B) जोगेश्वरी मंदिर", "C) मरीयामाई मंदिर", "D) अंबाबाई मंदिर"), 0),
            Question(37, "प्रश्न ३७: 'अष्टविनायक' तीर्थक्षेत्रांमध्ये किती मंदिरे आहेत?", listOf("A) 5", "B) 8", "C) 12", "D) 10"), 1),
            Question(38, "प्रश्न ३८: 'महाबळेश्वर' हा कोणत्या प्रकारच्या पर्वतांमध्ये आहे?", listOf("A) लघु पर्वत", "B) उंच पर्वत", "C) समशीतोष्ण पर्वत", "D) गड"), 1),
            Question(39, "प्रश्न ३९: 'सह्याद्री' पर्वतांचा विस्तार कोणत्या दिशेला आहे?", listOf("A) उत्तर", "B) दक्षिण", "C) पूर्व", "D) पश्चिम"), 3),
            Question(40, "प्रश्न ४०: 'धाराशिव' किल्ला कोणत्या नदीच्या किनाऱ्यावर आहे?", listOf("A) भीमा", "B) कृष्णा", "C) ताप्ती", "D) गोदावरी"), 0),
            Question(41, "प्रश्न ४१: 'सांगली' जिल्ह्यात कोणते प्रमुख गाजराचे पीक आहे?", listOf("A) पांढरे गाजर", "B) लाल गाजर", "C) काळे गाजर", "D) आंबा गाजर"), 1),
            Question(42, "प्रश्न ४२: 'उदगीर' किल्ला कोणत्या राजवंशाचा आहे?", listOf("A) चालुक्य", "B) यादव", "C) मुघल", "D) मराठा"), 1),
            Question(43, "प्रश्न ४३: 'मुंबई' शहरातील किती बेटांचा समावेश आहे?", listOf("A) 7", "B) 9", "C) 11", "D) 13"), 2),
            Question(44, "प्रश्न ४४: 'चिपळूण' कसे विशेष आहे?", listOf("A) ताजे फळांचे उत्पादन", "B) पाण्याचा प्रवाह", "C) समुद्री किनारा", "D) ऐतिहासिक स्थळे"), 2),
            Question(45, "प्रश्न ४५: 'पाटणा' किल्ला कोणत्या जिल्ह्यात आहे?", listOf("A) नाशिक", "B) पुणे", "C) ठाणे", "D) सोलापूर"), 3),
            Question(46, "प्रश्न ४६: 'धुळे' जिल्ह्यातील प्रमुख गहू उत्पादक गाव कोणते आहे?", listOf("A) पाचोरा", "B) पिंपळगाव", "C) नंदुरबार", "D) धुळे"), 1),
            Question(47, "प्रश्न ४७: 'नाशिक' मध्ये कोणत्या प्रकारच्या आंब्याचे उत्पादन जास्त आहे?", listOf("A) अल्फोन्सो", "B) केसर", "C) तुती", "D) बेंगळुरू"), 0),
            Question(48, "प्रश्न ४८: 'अलिबाग' समुद्री किनार्यावर कोणत्या प्रकारचा हवामान आहे?", listOf("A) उष्णकटिबंधीय", "B) समशीतोष्ण", "C) ध्रुवीय", "D) समुद्र किनारी"), 0),
            Question(49, "प्रश्न ४९: 'रत्नागिरी' जिल्ह्यातील कोणती प्रमुख डोंगररांगा आहे?", listOf("A) सह्याद्री", "B) विंध्य", "C) हिमालय", "D) अरेबियन"), 0),
            Question(50, "प्रश्न ५०: 'अम्बोली' या पर्यटन स्थळाचे वैशिष्ट्य काय आहे?", listOf("A) धबधबा", "B) समुद्र किनारा", "C) जंगली वन्यजीव", "D) बाग"), 0)
        )
    }

}