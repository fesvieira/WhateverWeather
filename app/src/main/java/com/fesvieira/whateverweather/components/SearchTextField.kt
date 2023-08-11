package com.fesvieira.whateverweather.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.fesvieira.whateverweather.R
import com.fesvieira.whateverweather.ui.theme.TextFieldBackground

@Composable
fun SearchTextField(
    textState: TextFieldValue,
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    onValueChange: (TextFieldValue) -> Unit,
    onSearchClick: () -> Unit,
    onFocus: (FocusState) -> Unit
) {
    FormsTextField(
        textState = textState,
        backgroundColor = TextFieldBackground,
        focusRequester = focusRequester,
        onValueChange = onValueChange,
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        focusManager.clearFocus()
                        onSearchClick()
                    }
                    .padding(12.dp)
            )
        },
        placeholder = "Type a place...",
        onDone = {
            focusManager.clearFocus()
            onSearchClick()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
            .onFocusChanged {
                onFocus(it)
            }
    )
}