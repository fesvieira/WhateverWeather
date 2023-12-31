package com.fesvieira.whateverweather.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.fesvieira.whateverweather.ui.theme.Gray
import com.fesvieira.whateverweather.ui.theme.MidnightBlue
import com.fesvieira.whateverweather.ui.theme.Typography

@Composable
fun FormsTextField(
    textState: TextFieldValue,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    onValueChange: (TextFieldValue) -> Unit = { _ -> },
    focusRequester: FocusRequester? = null,
    backgroundColor: Color = MidnightBlue,
    errorMessage: String? = null,
    onDone: (KeyboardActionScope) -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    OutlinedTextField(
        value = textState,
        onValueChange = { onValueChange(it) },
        modifier = modifier
            .then (
                if (focusRequester != null) {
                    Modifier.focusRequester(focusRequester)
                } else {
                    Modifier
                }
            ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            errorTextColor = Color.White,
            focusedPlaceholderColor = Gray,
            unfocusedPlaceholderColor = Gray,
            focusedContainerColor = backgroundColor,
            unfocusedContainerColor = backgroundColor,
            cursorColor = Color.White,
            focusedBorderColor = DarkGray,
            unfocusedBorderColor = DarkGray,
            errorBorderColor = DarkGray,
            errorSupportingTextColor = Color.Magenta,
            errorContainerColor = backgroundColor
        ),
        textStyle = Typography.bodyMedium,
        singleLine = true,
        shape = RoundedCornerShape(24.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, capitalization = KeyboardCapitalization.Words),
        keyboardActions = KeyboardActions(onDone = onDone),
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        isError = errorMessage != null,
        supportingText = {
            errorMessage?.let {
                Text(text = it)
            }
        },
        placeholder = {
            Text(placeholder ?: "", style = Typography.bodyMedium.copy(color = Gray))
        }
    )
}