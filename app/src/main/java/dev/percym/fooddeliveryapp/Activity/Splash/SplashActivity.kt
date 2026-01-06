package dev.percym.fooddeliveryapp.Activity.Splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import dev.percym.fooddeliveryapp.Activity.BaseActivity
import dev.percym.fooddeliveryapp.Activity.Dashboard.MainActivity
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.darkBrown))) {
        ConstraintLayout(modifier = Modifier.padding(top=48.dp)) {
            val(backgroundImg, logImg)=createRefs()
            Image(
                painter = painterResource(id= R.drawable.intro_pic),
                contentDescription = null,
                modifier = Modifier.constrainAs(backgroundImg){
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }.fillMaxWidth()
            )

            Image(
                painter = painterResource(id= R.drawable.pizza),
                contentDescription = null,
                modifier = Modifier.run{
                    constrainAs(logImg){
                        top.linkTo(backgroundImg.top)
                        bottom.linkTo(backgroundImg.bottom)
                        start.linkTo(backgroundImg.start)
                        end.linkTo(backgroundImg.end)
                    }
                }, contentScale = ContentScale.Fit
            )
        }
            val styledText = buildAnnotatedString {
                append("Welcome to your")
                withStyle(style = SpanStyle(color = colorResource(R.color.orange))){
                    append("food \n paradise")
                }
                append(" experience food perfection delivered")
            }
            Text(
                text = styledText,
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.white),
                modifier=Modifier.padding(top = 32.dp)
                    .padding(horizontal = 16.dp)
            )
            Text(
                text = stringResource(R.string.splashSubTitle),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.white),
                modifier=Modifier.padding(16.dp)
            )
            GetStartedButton(onGetStartedClick, modifier = Modifier.padding(16.dp) )


        }
    }

