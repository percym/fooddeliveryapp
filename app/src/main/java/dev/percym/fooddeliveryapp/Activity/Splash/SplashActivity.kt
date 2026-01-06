package dev.percym.fooddeliveryapp.Activity.Splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import dev.percym.fooddeliveryapp.Activity.BaseActivity
import dev.percym.fooddeliveryapp.MainActivity
import dev.percym.fooddeliveryapp.R


class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SplashScreen(
                onGetStartedClick =
                    { startActivity(Intent(this, MainActivity::class.java)) })
        }
    }
}
@Composable
@Preview
fun SplashScreen(onGetStartedClick:()->Unit={}){

    Column(modifier = Modifier.fillMaxSize().background(color = colorResource(R.color.darkBrown))) {
        ConstraintLayout(modifier = Modifier.padding(top=48.dp)) {
            val(backgroundImg, logImg)=createRefs()
            Image(
                painter = painterResource(id= R.drawable.intro_pic),
                contentDescription = null,
                modifier = Modifier.constrainAs(backgroundImg){
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
            )
        }
    }

}