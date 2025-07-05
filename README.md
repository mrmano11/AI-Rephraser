# AI Rephraser – Java Console Application

An intelligent Java-based console application that rephrases user input text into plagiarism-free alternatives using the **Cohere API**. This tool helps writers, students, and professionals enhance their writing while maintaining originality and fluency. It also estimates the **plagiarism level** in the original and rephrased text.

---

## 🧠 Features

- 🔄 Rephrases input sentences into clearer, original formats
- 🧪 Calculates estimated plagiarism level of both original and rephrased text
- 📜 Console-based interface (no GUI)
- ☁️ Uses **Cohere Generate API** for AI-powered paraphrasing
- 💡 Suitable for academic, creative, and business writing

---

## 🚀 How It Works

1. **User** enters a sentence via console.
2. **Java app** sends the input to Cohere's Generate API.
3. The response contains rephrased text alternatives.
4. The application calculates a rough plagiarism estimate (word overlap).
5. Both original and rephrased text, along with their scores, are displayed.

---

## 📂 Project Structure

ai-rephraser-java/
│
├── src/
│   └── Rephraser.java       
│
├── libs/                    
│   └── gson-2.8.9.jar
│
└── README.md

---

## 📌 Future Enhancements
-Add GUI using JavaFX

-Advanced plagiarism detection using APIs (e.g., Copyleaks, Grammarly)

-Batch mode for rephrasing multiple inputs

-Multiple language support

---

## 🧑‍💻 Author
Mohanarengan S
AI & Software Engineering Enthusiast | Pre-final year B.Tech Student


