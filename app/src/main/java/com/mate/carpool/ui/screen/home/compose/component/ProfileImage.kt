package com.mate.carpool.ui.screen.home.compose.component

import androidx.compose.foundation.Image
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.mate.carpool.R
import com.mate.carpool.ui.theme.MateTheme
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ProfileImage(
    profileImage:String,
    modifier: Modifier
){
    GlideImage(
        imageModel = { profileImage },
        modifier = modifier,
        loading = {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        },
        failure = {
            Image(
                painter = painterResource(id = R.drawable.ic_profile),
                contentDescription = null
            )
        },
        previewPlaceholder = R.drawable.ic_profile
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewProfileImage() =
    MateTheme {
        ProfileImage(profileImage = "", modifier = Modifier)
    }