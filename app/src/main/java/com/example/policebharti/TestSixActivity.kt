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

class TestSixActivity : AppCompatActivity() {

    private var currentQuestionIndex = 0
    private var score = 0
    private lateinit var questionList: List<Question>
    private lateinit var back: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_test_six)
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
            Question(1, "प्रश्न १: 'कविता' मध्ये शुद्धलेखन व शुद्धसंदेश कसा साधला जातो?", listOf("A) व्यक्तिमत्वातून", "B) भाषाशुद्धतेद्वारे", "C) चित्रकारितेद्वारे", "D) संगीताद्वारे"), 1),
            Question(2, "प्रश्न २: 'संपूर्ण जगाच्या नकाशामध्ये कोणत्या देशाची भौगोलिक स्थान विशेष आहे?', तो कोणता?", listOf("A) भारत", "B) चीन", "C) रूस", "D) अमेरिका"), 0),
            Question(3, "प्रश्न ३: 'धर्मवीर संभाजी महाराज' यांच्या जिविताची समाप्ती कधी झाली?", listOf("A) 1689", "B) 1700", "C) 1674", "D) 1659"), 0),
            Question(4, "प्रश्न ४: 'गणित' या विषयातील गडद आणि गडद गुणोत्तर म्हणजे काय?", listOf("A) अनिश्‍चित गुणोत्तर", "B) अत्याधुनिक गुणोत्तर", "C) समांतर गुणोत्तर", "D) अपर्ण गुणोत्तर"), 2),
            Question(5, "प्रश्न ५: 'नाट्यशास्त्र' या विषयाच्या संदर्भात कोणता लेखक सर्वश्रेष्ठ मानला जातो?", listOf("A) शं. ना. नवरे", "B) व. पु. काळे", "C) संत तुकाराम", "D) तुकाराम गडगड"), 1),

            // Ankaganit (Mathematics) Questions
            Question(6, "प्रश्न ६: पायथागोरस सिद्धांतानुसार, समकोण त्रिकोणात कोणती गणितीय संकल्पना वापरली जाते?", listOf("A) a^2 + b^2 = c^2", "B) a + b = c", "C) a * b = c", "D) a - b = c"), 0),
            Question(7, "प्रश्न ७: एका शालेय गटात 60% मुली आणि 40% मुले आहेत. जर 30 मुलांचे गट असतील तर मुलींची संख्या किती?", listOf("A) 18", "B) 12", "C) 24", "D) 20"), 2),
            Question(8, "प्रश्न ८: जर A = {1, 2, 3, 4} आणि B = {3, 4, 5, 6} असेल, तर A च्या B मधील संख्यांची किती सदस्यता आहे?", listOf("A) 2", "B) 3", "C) 1", "D) 4"), 0),
            Question(9, "प्रश्न ९: कोणत्या घटकाला 'गुणात्मक बदल' मानले जाते?", listOf("A) उंची", "B) वज्न", "C) रंग", "D) तापमान"), 2),
            Question(10, "प्रश्न १०: कोणती संख्या 2 च्या वर्गातील पूर्णांक आहे?", listOf("A) 3", "B) 4", "C) 5", "D) 6"), 1),

            // Buddhimatta Chachni (Logical Reasoning) Questions
            Question(11, "प्रश्न ११: 'सर्व फुलांचे रंग पांढरे असतात.' ह्या विधानाचे विलोम विधान काय?", listOf("A) काही फुलांचे रंग पांढरे आहेत", "B) सर्व फुलांचे रंग पांढरे नाहीत", "C) फुलांचे रंग पांढरे आहेत", "D) सर्व फुले कडू आहेत"), 1),
            Question(12, "प्रश्न १२: 'कोणतीही वस्तू वजनाने हलकी असली तरी तिचा आकार मोठा असू शकतो' ह्या विधानाचे सत्यापन कसे करता येईल?", listOf("A) आकृतीने", "B) गणिताने", "C) अभ्यासाने", "D) वस्तुनिष्ठ विचाराने"), 0),
            Question(13, "प्रश्न १३: 'कधीही पाण्यात उगम पावणारे वनस्पती' याचे शुद्ध अर्थ काय आहे?", listOf("A) जलचर", "B) थलचर", "C) आकाशीय", "D) भूचर"), 1),
            Question(14, "प्रश्न १४: 'सर्व मराठी लोकांना सांस्कृतिक धरोहर आहे' ह्या विधानातील अटी कोणत्या?", listOf("A) सांस्कृतिक", "B) आर्थिक", "C) सामाजिक", "D) सर्वात महत्त्वाचे"), 1),
            Question(15, "प्रश्न १५: 'शाळेतील सर्व विद्यार्थ्यांना परीक्षा देण्याची आवश्यकता आहे' ह्या विधानाचे उलट विधान काय आहे?", listOf("A) काही विद्यार्थ्यांना परीक्षा देण्याची आवश्यकता नाही", "B) सर्व विद्यार्थ्यांना परीक्षा द्यावी लागेल", "C) काही विद्यार्थ्यांना परीक्षा द्यावी लागेल", "D) सर्व विद्यार्थ्यांना परीक्षा देणे आवश्यक आहे"), 0),

            // Bhumati (Geography) Questions
            Question(16, "प्रश्न १६: 'संयुक्त राष्ट्रांचे मुख्यालय कोणत्या शहरात आहे?'", listOf("A) पॅरिस", "B) न्यूयॉर्क", "C) जिनिव्हा", "D) लंडन"), 1),
            Question(17, "प्रश्न १७: 'भारतातील भौगोलिक क्षेत्रफळ सर्वात मोठा राज्य कोणता आहे?'", listOf("A) उत्तर प्रदेश", "B) राजस्थान", "C) मध्य प्रदेश", "D) महाराष्ट्र"), 1),
            Question(18, "प्रश्न १८: 'हिमालय पर्वताच्या शिखरांपैकी कोणते शिखर सर्वात उंच आहे?'", listOf("A) कंचनजंगा", "B) नांगापरबात", "C) माउंट एव्हरेस्ट", "D) धवलागिरी"), 2),
            Question(19, "प्रश्न १९: 'वातावरणातील CO2 वायूची वाढ कोणत्या घटकामुळे होते?' ", listOf("A) वृक्षांची कत्तल", "B) औद्योगिकीकरण", "C) वाहतूक", "D) सर्व"), 0),
            Question(20, "प्रश्न २०: 'पृथ्वीच्या ध्रुवीय क्षेत्राचे तापमान' काय दर्शवते?", listOf("A) उष्णकटिबंधीय", "B) तापमान गती", "C) हिवाळा", "D) समशीतोष्ण"), 1),

            // Chalu Ghadamodi (Current Affairs) Questions
            Question(21, "प्रश्न २१: २०२४ च्या साली भारताचे नवीन राष्ट्रपती कोण असतील?", listOf("A) द्रौपदी मुर्मू", "B) नरेंद्र मोदी", "C) राहुल गांधी", "D) ममता बॅनर्जी"), 1),
            Question(22, "प्रश्न २२: भारतातील सौर ऊर्जा उत्पादकतेसाठी कोणता राज्य सर्वोच्च आहे?", listOf("A) गुजरात", "B) महाराष्ट्र", "C) मध्य प्रदेश", "D) कर्नाटका"), 0),
            Question(23, "प्रश्न २३: 'ताजमहल' च्या पुनरुत्थानासाठी कोणती योजना कार्यान्वित झाली?", listOf("A) स्मार्ट सिटी योजना", "B) वसतिगृह योजना", "C) सांस्कृतिक वारसा योजना", "D) पर्यटन योजना"), 2),
            Question(24, "प्रश्न २४: '2024 ऑलंपिक खेळ' कोणत्या शहरात होणार आहेत?", listOf("A) पॅरिस", "B) टोकियो", "C) बीजिंग", "D) लंडन"), 0),
            Question(25, "प्रश्न २५: २०२३ च्या 'ग्रॅमी पुरस्कार'मध्ये 'गाणं ऑफ द इयर' कोणत्या गाण्याला मिळाला?", listOf("A) 'Easy on Me'", "B) 'Stay'", "C) 'As It Was'", "D) 'About Damn Time'"), 1),

            // English Questions
            Question(26, "Question 26: Identify the correct sentence: ", listOf("A) Neither John nor his friends was here.", "B) Neither John nor his friends were here.", "C) Both John and his friends was here.", "D) Both John and his friends were here."), 1),
            Question(27, "Question 27: What does the word 'ubiquitous' mean?", listOf("A) Rare", "B) Present everywhere", "C) Hard to find", "D) Uncommon"), 1),
            Question(28, "Question 28: Choose the correct form: 'If I (be) you, I (accept) the offer.'", listOf("A) were, will accept", "B) am, accept", "C) were, would accept", "D) was, accept"), 2),
            Question(29, "Question 29: Which of the following sentences is grammatically correct?", listOf("A) I likes tea.", "B) I like tea and coffee.", "C) I like neither tea or coffee.", "D) I doesn't like tea."), 1),
            Question(30, "Question 30: Choose the appropriate preposition: 'She is good (___) mathematics.'", listOf("A) in", "B) at", "C) on", "D) for"), 1)
        )
    }

}
