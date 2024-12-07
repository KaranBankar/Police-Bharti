package com.example.policebharti

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.provider.CalendarContract.Colors
import android.util.TypedValue
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
import android.graphics.Color as Color1

class TestTwoActivity : AppCompatActivity() {

    private var currentQuestionIndex = 0
    private var score = 0
    private lateinit var questionList: List<Question>
    private lateinit var back: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_test_two)
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

        val selectedOptionId = radioGroup.checkedRadioButtonId
        if (selectedOptionId != -1) {
            val isCorrect = selectedOptionId == questionList[currentQuestionIndex].correctAnswerIndex
            if (isCorrect) {
                score++ // Increase the score for correct answer
               // Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
            } else {
                val correctAnswerIndex = questionList[currentQuestionIndex].correctAnswerIndex
                val correctAnswerText = (radioGroup.getChildAt(correctAnswerIndex) as? RadioButton)?.text.toString()
                //Toast.makeText(this, "Incorrect! Correct Answer: $correctAnswerText", Toast.LENGTH_SHORT).show()
            }

            currentQuestionIndex++
            radioGroup.clearCheck() // Clear the selected option for the next question

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
            Question(1, "प्रश्न १: 'स्वातंत्र्य' या शब्दाचा अर्थ कोणता?", listOf("A) बंधन", "B) मुक्तता", "C) असुरक्षा", "D) नियंत्रण"), 1),
            Question(2, "प्रश्न २: 'कविता' हा कोणत्या साहित्य प्रकाराचा एक भाग आहे?", listOf("A) कथा", "B) निबंध", "C) नाटक", "D) गीत"), 3),
            Question(3, "प्रश्न ३: 'रामायण' या ग्रंथात मुख्य पात्र कोणते आहे?", listOf("A) सीता", "B) हनुमान", "C) राम", "D) लक्ष्मण"), 2),
            Question(4, "प्रश्न ४: 'गुणगुण' शब्दाचा अर्थ कोणता?", listOf("A) शांतता", "B) वाद्य", "C) गाणे", "D) कथा"), 0),
            Question(5, "प्रश्न ५: 'संपूर्ण जग' या वाक्यातील विशेषण कोणते?", listOf("A) जग", "B) संपूर्ण", "C) आहे", "D) येते"), 1),

            // Ankaganit Questions
            Question(6, "प्रश्न ६: ५ * ८ = ?", listOf("A) 45", "B) 48", "C) 40", "D) 50"), 1),
            Question(7, "प्रश्न ७: एका त्रिकोणाच्या पायऱ्या किती आहेत?", listOf("A) 3", "B) 4", "C) 5", "D) 6"), 0),
            Question(8, "प्रश्न ८: कोणती संख्या २९ च्या पूर्णांकाने विभाज्य आहे?", listOf("A) 58", "B) 59", "C) 60", "D) 61"), 0),
            Question(9, "प्रश्न ९: २५% म्हणजे किती?", listOf("A) 1/4", "B) 1/5", "C) 1/3", "D) 1/6"), 0),
            Question(10, "प्रश्न १०: २^३ चा मूल्य किती आहे?", listOf("A) 4", "B) 6", "C) 8", "D) 10"), 2),

            // Buddhimatta Chachni Questions
            Question(11, "प्रश्न ११: खालीलपैकी कोणता अल्गोरिदम आहे?", listOf("A) बायनरी सर्च", "B) समुच्चय सिद्धांत", "C) डेटा संरचना", "D) भिन्न समीकरण"), 0),
            Question(12, "प्रश्न १२: एक त्रिकोणात एकाच बाजूच्या दोन्ही बाजू किती असू शकतात?", listOf("A) 3", "B) 4", "C) 5", "D) 6"), 0),
            Question(13, "प्रश्न १३: 30 च्या 50% म्हणजे किती?", listOf("A) 15", "B) 20", "C) 10", "D) 25"), 0),
            Question(14, "प्रश्न १४: 8, 16, 32, ... या अनुक्रमात पुढील संख्या कोणती असेल?", listOf("A) 48", "B) 64", "C) 80", "D) 100"), 1),
            Question(15, "प्रश्न १५: 15 चा वर्गमूळ कोणता आहे?", listOf("A) 225", "B) 15", "C) 30", "D) 20"), 0),

            Question(16, "प्रश्न 16: 'माज्या गाण्यांत एक सुर आहे' या वचनाचे मुख्य आशय काय आहे?", listOf("A) आत्मा", "B) प्रेम", "C) धैर्य", "D) संघर्ष"), 1),
            Question(17, "प्रश्न 17: 'रामकृष्ण परमहंस' यांच्या जीवनावर कोणता ग्रंथ प्रसिद्ध आहे?", listOf("A) 'रामकृष्ण-कथा'", "B) 'परमहंसाचे विचार'", "C) 'रामकृष्णांचे दर्शन'", "D) 'रामकृष्णजींचे कार्य'"), 2),
            Question(18, "प्रश्न 18: 'कविता आणि त्याची रचना' या विषयावर कोणती प्रमुख तत्त्वे आहेत?", listOf("A) छंद, रिती, विषय", "B) विषय, आशय, प्रतिमान", "C) प्रतिमा, छंद, संदर्भ", "D) सर्व पर्याय बरोबर आहेत"), 3),
            Question(19, "प्रश्न 19: 'वाचनाची महत्ता' या विषयावर कोणता निबंध प्रसिद्ध आहे?", listOf("A) 'वाचन आणि विचार'", "B) 'वाचनाचे लाभ'", "C) 'वाचनाची सवय'", "D) 'वाचनाची ताकद'"), 1),
            Question(20, "प्रश्न 20: 'संत ज्ञानेश्वरी' या ग्रंथात कोणत्या तत्त्वज्ञानाचा समावेश आहे?", listOf("A) अद्वैत तत्त्वज्ञान", "B) द्वैत तत्त्वज्ञान", "C) सृजनशीलता", "D) नैतिकता"), 1),

                    // Chalu Ghadamodi Questions
            Question(21, "प्रश्न २१: २०२३ च्या भारतातील लोकशाहीत कोणत्या पक्षाचा विजय झाला?", listOf("A) भाजप", "B) काँग्रेस", "C) आम आदमी पार्टी", "D) शिवसेना"), 0),
            Question(22, "प्रश्न २२: २०२३ मध्ये 'मिस युनिव्हर्स' चा किताब कोणाला मिळाला?", listOf("A) हरनाज संधू", "B) सुष्मिता सेन", "C) प्रिया अल्वारो", "D) यापैकी नाही"), 1),
            Question(23, "प्रश्न २३: २०२३ मध्ये कोणत्या देशात 'वर्ल्ड कप' स्पर्धा झाली?", listOf("A) भारत", "B) ऑस्ट्रेलिया", "C) इंग्लंड", "D) पाकिस्तान"), 0),
            Question(24, "प्रश्न २४: २०२३ मध्ये 'ऑस्कर' पुरस्कार कोणत्या चित्रपटाला मिळाला?", listOf("A) द फादर", "B) नो टाइम टू डाई", "C) द पावर ऑफ द डॉग", "D) यापैकी नाही"), 2),
            Question(25, "प्रश्न २५: २०२३ मध्ये भारतीय क्रिकेट संघाचे कर्णधार कोण होते?", listOf("A) विराट कोहली", "B) रोहित शर्मा", "C) एम.एस. धोनी", "D) यापैकी नाही"), 1),

            Question(26, "प्रश्न 26: 13, 26, 39, 52 या श्रेणीतील पुढील संख्या कोणती आहे?", listOf("A) 65", "B) 60", "C) 66", "D) 64"), 0),
            Question(27, "प्रश्न 27: 36 चे वर्गमूळ किती आहे?", listOf("A) 8", "B) 6", "C) 7", "D) 5"), 1),
            Question(28, "प्रश्न 28: एका समचतुर्भुजाच्या क्षेत्रफळाचा फॉर्म्युला काय आहे?", listOf("A) बाजू x बाजू", "B) 1/2 (आधार x उंची)", "C) लांबी x रुंदी", "D) 1/2 x (अर्धव्यास)^2"), 2),
            Question(29, "प्रश्न 29: 15 आणि 20 मधील महत्तम सामायिक गुणक कोणता आहे?", listOf("A) 4", "B) 3", "C) 5", "D) 6"), 2),
            Question(30, "प्रश्न 30: 6 च्या पाचपट किती आहे?", listOf("A) 36", "B) 30", "C) 25", "D) 24"), 1)

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
