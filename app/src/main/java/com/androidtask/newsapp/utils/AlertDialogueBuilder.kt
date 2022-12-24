package com.androidtask.newsapp.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.androidtask.newsapp.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

//OPEN ALERT DIALOGE FOR MESSAGE
@Composable
fun openAlertForMessage(title:String,message:String,confirmButtonListener:()-> Unit)
{
    AlertDialog(
        backgroundColor = Color.White,
        title = {
            Text(
                title,
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Text(
                message,
                color = Color.Black,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )
        },
        onDismissRequest = {  },
        confirmButton = {
            TextButton(
                onClick = {
                    confirmButtonListener.invoke()
                },
                content = {
                    Text(
                        stringResource(R.string.text_ok),
                        color = Color.Black,
                        fontSize = 11.sp
                    )
                }
            )
        }
    )
}

//OPEN LOADER DIALOGE ON NEWS HEADLINES API CALL
@Composable
fun openLoaderDialogue()
{
    Dialog(onDismissRequest = { /*TODO*/ },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(color = Color.White, shape = RoundedCornerShape(10.dp)),
                content = {
                    Text(
                        stringResource(R.string.text_loading_label),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(0.dp, 25.dp, 0.dp, 0.dp)
                    )

                    CircularProgressIndicator(
                        modifier = Modifier.wrapContentWidth()
                            .wrapContentHeight()
                            .padding(0.dp,10.dp,0.dp,25.dp)
                            .width(50.dp)
                            .height(50.dp)
                            .align(alignment = Alignment.CenterHorizontally),
                        color = Color.Black,
                        strokeWidth = 2.dp
                    )
                }
            )
        }
    )
}