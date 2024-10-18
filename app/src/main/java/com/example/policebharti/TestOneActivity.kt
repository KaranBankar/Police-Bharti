package com.example.policebharti

import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TestOneActivity : AppCompatActivity() {

    private var currentQuestionIndex = 0
    private lateinit var questionList: List<Question>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_test_one)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        questionList = loadQuestions() // Load or fetch questions

        displayQuestion(currentQuestionIndex)

        findViewById<Button>(R.id.btnNext).setOnClickListener {
            // Logic to move to next question
            currentQuestionIndex++
            if (currentQuestionIndex < questionList.size) {
                displayQuestion(currentQuestionIndex)
            } else {
                // End of quiz
                Toast.makeText(this, "Quiz finished!", Toast.LENGTH_SHORT).show()
            }
        }


        findViewById<Button>(R.id.btnNext).setOnClickListener {
            val selectedOptionId = findViewById<RadioGroup>(R.id.rgOptions).checkedRadioButtonId
            if (selectedOptionId != -1) {
                val isCorrect = selectedOptionId == questionList[currentQuestionIndex].correctAnswerIndex
                if (isCorrect) {
                    // Increase the score
                    Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
                } else {
                    // Show incorrect answer
                    Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show()
                }
                currentQuestionIndex++
                if (currentQuestionIndex < questionList.size) {
                    displayQuestion(currentQuestionIndex)
                } else {
                    // End of quiz
                    Toast.makeText(this, "Quiz finished!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show()
            }
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

    private fun loadQuestions(): List<Question> {
        return listOf(
            Question(1, "प्रश्न १: 'भारताची राजधानी दिल्ली आहे' या वाक्यातील नाव सर्वनाम कोणते?", listOf("A) दिल्ली", "B) आहे", "C) भारताची", "D) राजधानी"), 3),
            Question(2, "प्रश्न २: 'तो फार चांगला मुलगा आहे' या वाक्यातील विशेषण कोणते?", listOf("A) तो", "B) चांगला", "C) मुलगा", "D) आहे"), 1),
            Question(3,"प्रश्न ३: 'सूर्य पूर्वेला उगवतो' या वाक्यातील क्रियापद कोणते?", listOf("A) सूर्य","B) पूर्वेला","C) उगवतो","D) वाक्य नाही"),2),
            Question(4,"प्रश्न ४: 'सागर जसा शांत असतो, तसा तो आहे' या वाक्यातील तुलना कशाशी केली आहे?", listOf("A) वारा","B) आकाश","C) सागर","D) नदी"),2),
            Question(5,"प्रश्न ५: 'तिच्या' या शब्दाचा प्रकार कोणता आहे?", listOf("A) सर्वनाम","B) नाम","C) क्रियापद","D) विशेषण"),0),
            Question(6,"प्रश्न १: एका विशिष्ट सांकेतिक भाषेत 'CAT' शब्द 'DBU' असा लिहिला जातो. तर 'DOG' कसा लिहिला जाईल?", listOf("A) EPH","B) EPHI","C) DPH","D) EOG"),0),
            Question(7,"प्रश्न २: समजा, क्रमात असलेले 2, 4, 8, 16, ... यापुढील संख्या कोणती असेल?", listOf("A) 24","B) 30","C) 32","D) 40"),2),
            Question(8,"प्रश्न ३: तीन नावे एकाच वेळी नदीच्या पाण्यात उतरली. समजा, पाणी वर वर जाण्याच्या क्रमाने असलेल्या नावांचा क्रम 'C', 'B', 'A' आहे. तर सर्वात शेवटी कोणता नाव पाण्यात उतरेल?", listOf("A) A","B) B","C) C","D) नाव क्रमात नसतील"),0),
            Question(9,"प्रश्न ४: एका वेळी फक्त एका प्रश्नाचे उत्तर देता येईल. समजा, एका कोड्यात चार प्रश्न आहेत. यात उत्तर काढण्यासाठी किती वेळा प्रश्न विचारावे लागतील?", listOf("A) 1","B) 2","C) 3","D) 4"),3),
            Question(10,"प्रश्न ५: एका खोलीत 4 माणसे बसलेली आहेत. जर प्रत्येकाने प्रत्येकाशी हस्तांदोलन केले, तर एकूण हस्तांदोलन किती होतील?", listOf("A) 6","B) 12","C) 10","D) 8"),0),
            Question(11,"प्रश्न १: ८, १२, २०, ३२ या अंकांत पुढील संख्या कोणती असेल?", listOf("A) ४८","B) ५०","C) ४२","D) ४०"),3),
            Question(12,"प्रश्न २: ५ कि.मी. चे मीटर मध्ये रूपांतर करा.", listOf("A) ५०० मीटर","B) ५००० मीटर","C) ५५०० मीटर","D) ५०००० मीटर"),1),
            Question(13,"प्रश्न ३: १२x१२ किती आहे?", listOf("A) १४४","B) १२८","C) १५६","D) १३२"),0),
            Question(14,"प्रश्न ४: एका संख्येचे २०% म्हणजे ५० आहे. तर ती संख्या किती असेल?", listOf("A) २००","B) २५०","C) १००","D) १५०"),0),
            Question(15,"प्रश्न ५: एका वस्त्रावर ४०% सूट आहे, तर ५०० रुपयांच्या वस्त्रावर सवलतीनंतरची किंमत किती?", listOf("A) ३००","B) ४००","C) २००","D) ३५०"),0),
            Question(16,"प्रश्न १: एका समभुज त्रिकोणातील प्रत्येक कोनाचे माप किती आहे?", listOf("A) 90°","B) 60°","C) 120°","D) 45°"),1),
            Question(17,"प्रश्न २: एका त्रिकोणाच्या दोन बाजू अनुक्रमे 7 आणि 9 आहेत. जर तिसरी बाजू 12 असेल, तर त्रिकोणाचे क्षेत्रफळ शोधा. (हीरोनचे सूत्र वापरा)", listOf("A) 30","B) 18","C) 27.93","D) 24"),2),
            Question(18,"प्रश्न ३: एका वृत्ताच्या एका तुकड्याचे क्षेत्रफळ 50 मीटर^2 आहे आणि त्याचा कोन 60° आहे. तर संपूर्ण वृत्ताचे क्षेत्रफळ किती आहे?", listOf("A) 300 मीटर^2","B) 360 मीटर^2","C) 180 मीटर^2","D) 150 मीटर^2"),0),
            Question(19,"6. Who is Father Of Indian Constitution ?", listOf("Nehru","Dr.Ambedkar","Sir Jedeja","Mohit Pilani"),1),
            Question(20,"6. Who is Father Of Indian Constitution ?", listOf("Nehru","Dr.Ambedkar","Sir Jedeja","Mohit Pilani"),1),
            Question(21,"6. Who is Father Of Indian Constitution ?", listOf("Nehru","Dr.Ambedkar","Sir Jedeja","Mohit Pilani"),1),
            Question(22,"6. Who is Father Of Indian Constitution ?", listOf("Nehru","Dr.Ambedkar","Sir Jedeja","Mohit Pilani"),1),
            Question(23,"6. Who is Father Of Indian Constitution ?", listOf("Nehru","Dr.Ambedkar","Sir Jedeja","Mohit Pilani"),1),
            Question(24,"6. Who is Father Of Indian Constitution ?", listOf("Nehru","Dr.Ambedkar","Sir Jedeja","Mohit Pilani"),1),
            Question(25,"6. Who is Father Of Indian Constitution ?", listOf("Nehru","Dr.Ambedkar","Sir Jedeja","Mohit Pilani"),1),


        )
    }
}