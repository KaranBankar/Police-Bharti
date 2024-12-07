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

class TestThreeActivity : AppCompatActivity() {

    private var currentQuestionIndex = 0
    private var score = 0
    private lateinit var questionList: List<Question>
    private lateinit var back: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_test_three)
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
        val radioGroup = findViewById<RadioGroup>(R.id.rgOptions)
        val selectedOptionId = findViewById<RadioGroup>(R.id.rgOptions).checkedRadioButtonId

        if (selectedOptionId != -1) {
            val isCorrect = selectedOptionId == questionList[currentQuestionIndex].correctAnswerIndex
            if (isCorrect) {
                score++ // Increase the score for correct answer
                //Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
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
            Question(1, "प्रश्न १: 'पुस्तक' चा अर्थ काय?", listOf("A) लेखनाचा साधन", "B) वाचनाचा साधन", "C) ज्ञानाचा स्त्रोत", "D) सर्व"), 3),
            Question(2, "प्रश्न २: 'संपूर्ण' या वाक्यातील विशेषण कोणते?", listOf("A) जग", "B) आहे", "C) सर्व", "D) म्हणून"), 2),
            Question(3, "प्रश्न ३: 'नदी' च्या अलीकडील शब्दाचा अर्थ काय?", listOf("A) नदीचा प्रवाह", "B) ताजगी", "C) जलधार", "D) सर्व"), 0),
            Question(4, "प्रश्न ४: 'कविता' म्हणजे काय?", listOf("A) कथा", "B) गाणे", "C) भावना व्यक्त करणारे लेखन", "D) माहिती"), 2),
            Question(5, "प्रश्न ५: 'भाषा' म्हणजे काय?", listOf("A) संवाद साधण्याचे माध्यम", "B) सांस्कृतिक वैशिष्ट्य", "C) लोकांचे विचार", "D) सर्व"), 3),

            // Ankaganit (Mathematics) Questions
            Question(6, "प्रश्न ६: ७ + ५ = ?", listOf("A) 10", "B) 11", "C) 12", "D) 13"), 2),
            Question(7, "प्रश्न ७: १५ चा २०% म्हणजे किती?", listOf("A) 2", "B) 3", "C) 4", "D) 5"), 3),
            Question(8, "प्रश्न ८: ६ * ९ = ?", listOf("A) 54", "B) 60", "C) 64", "D) 72"), 0),
            Question(9, "प्रश्न ९: १० चा वर्ग कोणता आहे?", listOf("A) 90", "B) 100", "C) 80", "D) 70"), 1),
            Question(10, "प्रश्न १०: १/२ + १/२ = ?", listOf("A) 1", "B) 2", "C) 1/2", "D) 0"), 0),

            // Buddhimatta Chachni (Logical Reasoning) Questions
            Question(11, "प्रश्न ११: 'सर्व माणसं मर्त्य आहेत' या विधानाचा विरोधी विधान काय?", listOf("A) काही माणसं अमर आहेत", "B) सर्व माणसं अमर आहेत", "C) काही माणसं मर्त्य आहेत", "D) सर्व माणसं मृत्यूस येणार नाहीत"), 0),
            Question(12, "प्रश्न १२: 'नाही' म्हणजे कोणते?", listOf("A) असामान्य", "B) नकारात्मक", "C) सकारात्मक", "D) तटस्थ"), 1),
            Question(13, "प्रश्न १३: ८ = २x, x = ?", listOf("A) 2", "B) 4", "C) 6", "D) 8"), 1),
            Question(14, "प्रश्न १४: 'बोलणे' या क्रियापदाचा भूतकाळ कोणता?", listOf("A) बोलतो", "B) बोलेन", "C) बोललो", "D) बोलत आहे"), 2),
            Question(15, "प्रश्न १५: जर A > B असेल आणि B > C असेल, तर A > C हे सत्य आहे का?", listOf("A) हो", "B) नाही", "C) कदाचित", "D) ते सिद्ध करणे आवश्यक आहे"), 0),

            Question(16, "प्रश्न 16: 'नाटक आणि समाज' या विषयावर कोणता निबंध प्रसिद्ध आहे?", listOf("A) 'नाटकाची महत्ता'", "B) 'नाटककारांचे योगदान'", "C) 'नाटकाचे रूप' ", "D) 'नाटकांचे दार्शनिक मूल्य' "), 1),
            Question(17, "प्रश्न 17: 'गोदावरी' या कवितेतील मुख्य विचार काय आहे?", listOf("A) नदीची सुंदरता", "B) सांस्कृतिक वारसा", "C) पर्यावरण संरक्षण", "D) सर्व पर्याय बरोबर आहेत"), 3),
            Question(18, "प्रश्न 18: 'अखेरचा सवाल' या कथेचा लेखक कोण आहे?", listOf("A) शं. ना. नवरे", "B) व. पु. काळे", "C) रणजीत देसाई", "D) पु. ल. देशपांडे"), 0),
            Question(19, "प्रश्न 19: 'कवी रांजण्या' या काव्यात मुख्यतः कोणत्या भावनांचा प्रगट आहे?", listOf("A) प्रेम", "B) वेदना", "C) आशा", "D) नैराश्य"), 1),
            Question(20, "प्रश्न 20: 'साहित्य आणि संस्कृती' या विषयावर चर्चा करणारा लेखक कोण आहे?", listOf("A) ना. सि. फडके", "B) ग. दि. माडगूळकर", "C) विष्णू सखाराम खांडेकर", "D) कुसुमाग्रज"), 2),

                    // Chalu Ghadamodi (Current Affairs) Questions
            Question(21, "प्रश्न २१: २०२३ मध्ये भारतात कोणत्या पक्षाचा विजय झाला?", listOf("A) भाजप", "B) काँग्रेस", "C) आम आदमी पार्टी", "D) शिवसेना"), 0),
            Question(22, "प्रश्न २२: २०२३ मध्ये 'मिस युनिव्हर्स' चा किताब कोणाला मिळाला?", listOf("A) हरनाज संधू", "B) सुष्मिता सेन", "C) प्रिया अल्वारो", "D) यापैकी नाही"), 1),
            Question(23, "प्रश्न २३: २०२३ मध्ये कोणत्या देशात 'वर्ल्ड कप' स्पर्धा झाली?", listOf("A) भारत", "B) ऑस्ट्रेलिया", "C) इंग्लंड", "D) पाकिस्तान"), 0),
            Question(24, "प्रश्न २४: २०२३ मध्ये 'ऑस्कर' पुरस्कार कोणत्या चित्रपटाला मिळाला?", listOf("A) द फादर", "B) नो टाइम टू डाई", "C) द पावर ऑफ द डॉग", "D) यापैकी नाही"), 2),
            Question(25, "प्रश्न २५: २०२३ मध्ये भारतीय क्रिकेट संघाचे कर्णधार कोण होते?", listOf("A) विराट कोहली", "B) रोहित शर्मा", "C) एम.एस. धोनी", "D) यापैकी नाही"), 1),

            Question(26, "प्रश्न 26: 72 चा अर्धा किती आहे?", listOf("A) 36", "B) 40", "C) 34", "D) 32"), 0),
            Question(27, "प्रश्न 27: 81 चे वर्गमूळ किती आहे?", listOf("A) 7", "B) 8", "C) 9", "D) 10"), 2),
            Question(28, "प्रश्न 28: 25 टक्के म्हणजे कसे लिहिले जाईल?", listOf("A) 1/2", "B) 1/3", "C) 1/4", "D) 1/5"), 2),
            Question(29, "प्रश्न 29: एका आयताचा परिघ काढण्यासाठी योग्य सूत्र कोणते?", listOf("A) लांबी x रुंदी", "B) 2 x (लांबी + रुंदी)", "C) लांबी + रुंदी", "D) लांबी - रुंदी"), 1),
            Question(30, "प्रश्न 30: 15 आणि 25 यांचा ल.सा.वि. किती आहे?", listOf("A) 75", "B) 60", "C) 45", "D) 50"), 0)


        )
    }
    private fun showScoreDialog(score: Int) {
        val dialogView = layoutInflater.inflate(R.layout.custom_score_dialog, null)
        val tvScore = dialogView.findViewById<TextView>(R.id.tvScore)
        val btnOk = dialogView.findViewById<Button>(R.id.btnOk)
        tvScore.text = "Your Score is $score/${questionList.size}"
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        val dialog = builder.create()
        dialog.setCancelable(false)

        btnOk.setOnClickListener {
            dialog.dismiss()
            resetQuiz()
        }
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        var i=Intent(this,HomeActivity::class.java)
        startActivity(i)
        finish()
    }
}
