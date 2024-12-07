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

class TestFourActivity : AppCompatActivity() {
    private var currentQuestionIndex = 0
    private var score = 0
    private lateinit var questionList: List<Question>
    private lateinit var back: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_test_four)
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
                //Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
            } else {
                //Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show()
            }

            currentQuestionIndex++
            val radioGroup = findViewById<RadioGroup>(R.id.rgOptions)
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
            Question(1, "प्रश्न १: 'सांस्कृतिक वारसा' म्हणजे काय?", listOf("A) भौगोलिक विशेषता", "B) आर्थिक विकास", "C) पारंपरिक विचार आणि मूल्ये", "D) वैज्ञानिक ज्ञान"), 2),
            Question(2, "प्रश्न २: 'आधुनिक मराठी कविता' याबद्दल खालील विधानांमध्ये कोणते बरोबर आहे?", listOf("A) फक्त एकाच विषयावर आधारित", "B) सामाजिक मुद्द्यांना उचलते", "C) फक्त ऐतिहासिक कवितांचा समावेश", "D) केवळ लघुनिबंध"), 1),
            Question(3, "प्रश्न ३: 'माणूस' शब्दात कितती व्यंजने आहेत?", listOf("A) 2", "B) 3", "C) 4", "D) 5"), 1),
            Question(4, "प्रश्न ४: 'विविधतेमध्ये एकता' ह्या तत्त्वाचा मुख्य आधार कोणता?", listOf("A) एकता", "B) असमानता", "C) भिन्नता", "D) लोकशाही"), 0),
            Question(5, "प्रश्न ५: 'शोधक' या गझलमध्ये कविनं काय दर्शवले?", listOf("A) प्रेम", "B) दुःख", "C) शोध", "D) असंतोष"), 3),

            // Ankaganit (Mathematics) Questions
            Question(6, "प्रश्न ६: ५x + 2 = 12, x चा मूल्य काय?", listOf("A) 2", "B) 3", "C) 4", "D) 5"), 1),
            Question(7, "प्रश्न ७: १२, १५, २० चा LCM काय आहे?", listOf("A) 60", "B) 120", "C) 180", "D) 240"), 1),
            Question(8, "प्रश्न ८: कोणत्या संख्येचा वर्ग 121 आहे?", listOf("A) 10", "B) 11", "C) 12", "D) 13"), 1),
            Question(9, "प्रश्न ९: त्रिकोणाचे क्षेत्रफळ मोजण्यासाठी योग्य सूत्र काय?", listOf("A) 1/2 * आधार * उंची", "B) आधार * उंची", "C) 2 * (आधार + उंची)", "D) 1/3 * आधार * उंची"), 0),
            Question(10, "प्रश्न १०: २०% म्हणजे किती?", listOf("A) 0.2", "B) 0.25", "C) 0.3", "D) 0.15"), 0),

            // Buddhimatta Chachni (Logical Reasoning) Questions
            Question(11, "प्रश्न ११: 'सर्व पुरुष माणसे आहेत' ह्या विधानाचा विलोम विधान काय?", listOf("A) काही पुरुष माणसे नाहीत", "B) सर्व पुरुष माणसे आहेत", "C) सर्व माणसे पुरुष आहेत", "D) काही पुरुष माणसे आहेत"), 0),
            Question(12, "प्रश्न १२: जर P => Q असेल, तर Q => P हे सत्य आहे का?", listOf("A) हो", "B) नाही", "C) कदाचित", "D) नेहमीच"), 1),
            Question(13, "प्रश्न १३: 'तुम्ही येत नसल्यास मी गप्प राहीन' या विधानातील अटी कोणत्या?", listOf("A) यथार्थता", "B) सर्त", "C) प्रतिकूलता", "D) संबंध"), 1),
            Question(14, "प्रश्न १४: जर A आणि B दोन्ही खरे असतील, तर A OR B हे काय असेल?", listOf("A) खरे", "B) खोटे", "C) कदाचित खरे", "D) प्रतिकूल"), 0),
            Question(15, "प्रश्न १५: 'कित्येक' म्हणजे कोणते?", listOf("A) एकटं", "B) अधिकतम", "C) अनंत", "D) अज्ञात"), 1),

            Question(16, "प्रश्न 16: ‘संत ज्ञानेश्वरी’ या ग्रंथाचे महत्व काय आहे?", listOf("A) भक्ति साहित्य", "B) तत्त्वज्ञान", "C) आत्मज्ञान", "D) सर्व पर्याय बरोबर आहेत"), 3),
            Question(17, "प्रश्न 17: ‘कृष्णकुमार’ या कथेचा लेखक कोण आहे?", listOf("A) व. पु. काळे", "B) शं. ना. नवरे", "C) फडके", "D) पु. ल. देशपांडे"), 0),
            Question(18, "प्रश्न 18: ‘निसर्ग’ या कविता संग्रहाचे लेखक कोण आहेत?", listOf("A) ना. सि. फडके", "B) व. पु. काळे", "C) ग. दि. माडगूळकर", "D) कुसुमाग्रज"), 3),
            Question(19, "प्रश्न 19: 'अक्लांत शुद्ध' या निबंधात लेखकाने कोणत्या विषयावर विचार केला आहे?", listOf("A) मनुष्य जीवन", "B) शिक्षण", "C) निसर्ग", "D) तंत्रज्ञान"), 1),
            Question(20, "प्रश्न 20: ‘कविता’ या साहित्यातील प्रमुख अंग कोणती?", listOf("A) रूपक", "B) छंद", "C) विषय", "D) सर्व पर्याय बरोबर आहेत"), 3),

            // Chalu Ghadamodi (Current Affairs) Questions
            Question(21, "प्रश्न २१: २०२४ च्या संसदीय निवडणुकांमध्ये कोणता पक्ष प्रमुख आहे?", listOf("A) भारतीय जनता पक्ष", "B) राष्ट्रवादी काँग्रेस", "C) काँग्रेस", "D) आम आदमी पार्टी"), 0),
            Question(22, "प्रश्न २२: २०२३ च्या अर्थसंकल्पात कोणत्या योजनेची घोषणा झाली?", listOf("A) प्रधानमंत्री किसान सन्मान निधी", "B) जन धन योजना", "C) स्वच्छ भारत मिशन", "D) उज्वला योजना"), 1),
            Question(23, "प्रश्न २३: २०२३ मध्ये 'नोबेल शांतता पुरस्कार' कोणाला मिळाला?", listOf("A) मलाला यूसुफजई", "B) डेव्हिड अटेंबरो", "C) यापैकी नाही", "D) लिओनार्डो डिकॅप्रिओ"), 2),
            Question(24, "प्रश्न २४: २०२४ मध्ये ओलंपिक खेळ कोणत्या शहरात होणार आहेत?", listOf("A) टोकियो", "B) पॅरिस", "C) लॉस एंजेलिस", "D) बीजिंग"), 1),
            Question(25, "प्रश्न २५: २०२२ च्या शांती संधीसाठी कोणता देश निवडला गेला?", listOf("A) जर्मनी", "B) भारत", "C) अमेरिका", "D) युनायटेड किंगडम"), 1),

            Question(26, "प्रश्न 26: 75 चा 40% किती आहे?", listOf("A) 30", "B) 25", "C) 35", "D) 40"), 0),
            Question(27, "प्रश्न 27: 9 आणि 12 यांचा ल.सा.वि. किती आहे?", listOf("A) 18", "B) 24", "C) 36", "D) 12"), 0),
            Question(28, "प्रश्न 28: 5 ते 15 पर्यंत असणाऱ्या विषम संख्यांची बेरीज किती आहे?", listOf("A) 50", "B) 40", "C) 60", "D) 45"), 3),
            Question(29, "प्रश्न 29: पाई (π) चा सुमारे काय मान आहे?", listOf("A) 3.12", "B) 3.14", "C) 3.16", "D) 3.18"), 1),
            Question(30, "प्रश्न 30: 144 चे अर्धे किती आहे?", listOf("A) 66", "B) 68", "C) 70", "D) 72"), 3)

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
