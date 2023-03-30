package com.example.chatengine.QuestionRoom

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun QuestionList(questionViewModel: QuestionViewModel = viewModel()) {
    val expandedQuestion = remember { mutableStateOf<Question?>(null) }

    val questions by questionViewModel.questions.observeAsState(emptyList())
    
    LazyColumn(
        modifier = Modifier.padding(16.dp).height(650.dp)
    ) {
        items(questions) { question ->
//            if(question.subQuestions==null) {
            Text(text = question.title)
//            }

//            else {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { expandedQuestion.value = question },
                elevation = 8.dp
            ) {
                Text(
                    text = question.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )
            }
//            }

            if (expandedQuestion.value == question) {
                question.subQuestions.forEach { subQuestion ->
                    SubQuestion(
                        subQuestion = subQuestion,
                        onSubQuestionClick = { expandedQuestion.value = question }
                    )
                }
            }
        }
    }

    Box (modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter){
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Chat With Agent")

        }
    }

    LaunchedEffect(Unit) {
        questionViewModel.loadQuestions()
    }
}

@Composable
fun SubQuestion(
    subQuestion: SubQuestion,
    onSubQuestionClick: () -> Unit
) {
    var expandedSubQuestion by remember { mutableStateOf(false) }

//Text(text = subQuestion.subQuestions.toString())
    if(subQuestion.subQuestions?.isEmpty() == true){
        Row() {
            Icon(Icons.Default.ArrowForward, contentDescription = "Solution")
            Text(
                text = subQuestion.question,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
        }
    }
    else {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp)
                .clickable {
                    expandedSubQuestion = !expandedSubQuestion
                    onSubQuestionClick()
                },
            elevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = subQuestion.question,
                    fontStyle = FontStyle.Italic,
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                if (expandedSubQuestion) {
                    subQuestion.subQuestions?.forEach { nestedSubQuestion ->
                        SubQuestion(
                            subQuestion = nestedSubQuestion,
                            onSubQuestionClick = onSubQuestionClick
                        )
                    }
                }
            }
        }
    }
}