package com.theseuntaylor.minipe.core.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.theseuntaylor.minipe.core.TextFieldState

@Composable
fun TextFieldError(textError: String) {
    Text(
        text = textError,
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.error
    )
}

@Preview
@Composable
fun TextFieldErrorPreview() {
    TextFieldError(textError = "There was an error!")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PassEntryTextField(
    modifier: Modifier = Modifier,
    label: Int,
    textFieldState: TextFieldState,
    imeAction: ImeAction = ImeAction.Next,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        imeAction = imeAction,
        keyboardType = KeyboardType.Text
    ),
    onImeAction: () -> Unit = {},
) {
    TextField(
        value = textFieldState.text,
        onValueChange = {
            textFieldState.text = it
        },
        label = {
            Text(
                text = stringResource(id = label),
                style = MaterialTheme.typography.bodyMedium,
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                textFieldState.onFocusChange(focusState.isFocused)
                if (!focusState.isFocused) {
                    textFieldState.enableShowErrors()
                }
            },
        textStyle = MaterialTheme.typography.bodyMedium,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
        ),
        isError = textFieldState.showErrors(),
        supportingText = {
            textFieldState.getError()?.let { error -> TextFieldError(textError = error) }
        },

        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
            onDone = {
                onImeAction()
            }
        ),
        visualTransformation = if (keyboardOptions.keyboardType == KeyboardType.Password) {
            if (textFieldState.isPasswordVisible) VisualTransformation.None
            else PasswordVisualTransformation()
        } else VisualTransformation.None,
        trailingIcon = {
            if (keyboardOptions.keyboardType == KeyboardType.Password) {

                val image = painterResource(textFieldState.showTrailingIcon())
                val description = stringResource(textFieldState.showTrailingIconDescription())

                IconButton(onClick = {
                    textFieldState.isPasswordVisible = !textFieldState.isPasswordVisible
                }) {
                    Icon(painter = image, description)
                }
            }
        },

        )
    // textFieldState.getError()?.let { error -> com.theseuntaylor.minipe.core.composables.TextFieldError(textError = error) }
}