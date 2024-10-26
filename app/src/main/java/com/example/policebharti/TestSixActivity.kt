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
        val radioGroup = findViewById<RadioGroup>(R.id.rgOptions)
        if (selectedOptionId != -1) {
            val isCorrect = selectedOptionId == questionList[currentQuestionIndex].correctAnswerIndex
            if (isCorrect) {
                score++ // Increase the score for correct answer
               // Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
            } else {
                //Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show()
            }

            currentQuestionIndex++
            radioGroup.clearCheck()
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

            Question(16, "प्रश्न 16: 'दिवसाचे चोविस तास' या कवितेत मुख्यतः कोणत्या विषयावर चर्चा केली आहे?", listOf("A) निसर्ग", "B) मानव जीवन", "C) काळाची महत्ता", "D) समाजातील समस्या"), 2),
            Question(17, "प्रश्न 17: 'पायाची गोष्ट' या कथेचा लेखक कोण आहे?", listOf("A) द. मा. मिरासदार", "B) शं. ना. नवरे", "C) व. पु. काळे", "D) पु. ल. देशपांडे"), 0),
            Question(18, "प्रश्न 18: 'महात्मा गांधी' यांच्या जीवनावर आधारित कोणता ग्रंथ प्रसिद्ध आहे?", listOf("A) 'गांधी: द लास्ट स्टेप' ", "B) 'गांधीजींचे विचार' ", "C) 'गांधी: अ बायोग्राफी' ", "D) 'गांधी: अ मॅन' "), 2),
            Question(19, "प्रश्न 19: 'संगणक व साहित्य' या लेखात कोणत्या गोष्टींचा समावेश केला आहे?", listOf("A) संगणकाची भूमिका", "B) साहित्य निर्मिती", "C) लेखनाचे तंत्र", "D) सर्व पर्याय बरोबर आहेत"), 3),
            Question(20, "प्रश्न 20: 'शब्दावरील प्रेम' या निबंधात लेखकाने कोणत्या विषयावर चर्चा केली आहे?", listOf("A) भाषाशुद्धता", "B) भाषा आणि संस्कृती", "C) संवाद कौशल्य", "D) लेखनाचे महत्त्व"), 1),

                    // Chalu Ghadamodi (Current Affairs) Questions
            Question(21, "प्रश्न २१: २०२४ च्या साली भारताचे नवीन राष्ट्रपती कोण असतील?", listOf("A) द्रौपदी मुर्मू", "B) नरेंद्र मोदी", "C) राहुल गांधी", "D) ममता बॅनर्जी"), 1),
            Question(22, "प्रश्न २२: भारतातील सौर ऊर्जा उत्पादकतेसाठी कोणता राज्य सर्वोच्च आहे?", listOf("A) गुजरात", "B) महाराष्ट्र", "C) मध्य प्रदेश", "D) कर्नाटका"), 0),
            Question(23, "प्रश्न २३: 'ताजमहल' च्या पुनरुत्थानासाठी कोणती योजना कार्यान्वित झाली?", listOf("A) स्मार्ट सिटी योजना", "B) वसतिगृह योजना", "C) सांस्कृतिक वारसा योजना", "D) पर्यटन योजना"), 2),
            Question(24, "प्रश्न २४: '2024 ऑलंपिक खेळ' कोणत्या शहरात होणार आहेत?", listOf("A) पॅरिस", "B) टोकियो", "C) बीजिंग", "D) लंडन"), 0),
            Question(25, "प्रश्न २५: २०२३ च्या 'ग्रॅमी पुरस्कार'मध्ये 'गाणं ऑफ द इयर' कोणत्या गाण्याला मिळाला?", listOf("A) 'Easy on Me'", "B) 'Stay'", "C) 'As It Was'", "D) 'About Damn Time'"), 1),

            Question(26, "प्रश्न 26: 144 चे वर्गमूळ किती आहे?", listOf("A) 10", "B) 12", "C) 14", "D) 16"), 1),
            Question(27, "प्रश्न 27: 56 मध्ये 7 ने भाग केल्यास उत्तर काय येईल?", listOf("A) 6", "B) 7", "C) 8", "D) 9"), 2),
            Question(28, "प्रश्न 28: 5, 10, 15, 20 या श्रेणीतील पुढील संख्या कोणती?", listOf("A) 21", "B) 22", "C) 25", "D) 30"), 2),
            Question(29, "प्रश्न 29: 1 ते 50 पर्यंत किती सम संख्यांचा समावेश होतो?", listOf("A) 25", "B) 26", "C) 24", "D) 23"), 1),
            Question(30, "प्रश्न 30: एका वृत्ताचा परीघ शोधण्यासाठी योग्य सूत्र कोणते आहे?", listOf("A) π x त्रिज्या", "B) 2π x त्रिज्या", "C) π x व्यास", "D) 2π x व्यास"), 1)

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

    override fun onBackPressed() {
        super.onBackPressed()
        var i=Intent(this,HomeActivity::class.java)
        startActivity(i)
        finish()
    }

}
