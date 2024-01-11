package com.theseuntaylor.minipe.lib_login.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.theseuntaylor.minipe.R
import com.theseuntaylor.minipe.core.composables.Loader
import com.theseuntaylor.minipe.core.composables.PassEntryTextField
import com.theseuntaylor.minipe.core.composables.VerticalSpacer

@Composable
fun LoginScreen(
    navigateToTaps: () -> Unit,
    loginViewModel: LoginViewModel = hiltViewModel(),
) {
    val focusManager = LocalFocusManager.current

    val context = LocalContext.current
    val state by loginViewModel.loginState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = state.errorMessage) {
        state.errorMessage?.let { error ->
            Toast.makeText(
                context, error, Toast.LENGTH_LONG
            ).show()
        }
    }
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_pass_entry),
            contentDescription = stringResource(
                id = R.string.img_pe_content_desc
            )
        )
        Column {
            PassEntryTextField(
                label = R.string.username,
                textFieldState = loginViewModel.usernameTextFieldState
            )
            VerticalSpacer(8.dp)
            PassEntryTextField(
                label = R.string.password,
                imeAction = ImeAction.Done,
                onImeAction = { focusManager.clearFocus() },
                textFieldState = loginViewModel.passwordTextFieldState,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
        }
        if (state.inProgress) Loader() else Button(
            onClick = loginViewModel::login,
            modifier = Modifier.fillMaxWidth(),
            enabled = loginViewModel.isButtonEnabled,
        ) {
            Text(
                text = stringResource(id = R.string.login),
            )
        }
    }

    LaunchedEffect(key1 = state) {
        if (state.loginSuccessful) {
            navigateToTaps()
        }
    }
}