package com.example.moviedb.ui.theme.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviedb.R

@Composable
fun StartScreen(
    onOptionClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(16.dp)
    ) {
        Column {
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = onOptionClicked,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.popular))
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun StartScreenPreview() {
    StartScreen(
        {}
    )
}