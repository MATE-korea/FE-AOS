package com.mate.carpool.ui.compose

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mate.carpool.R
import com.mate.carpool.data.vm.HomeCarpoolListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                HomeCarpoolSheet()
            }
        }
    }
}

object Colors{
    val Blue_007AFF = Color(0xFF007AFF)
    val Red_E0302D = Color(0xFFE0302D)
    val Gray_DADDE1 = Color(0xFFDADDE1)
    val Gray_A2ABB4 = Color(0xFFA2ABB4)
    val Gray_4E5760 = Color(0xFF4E5760)

}

@OptIn(ExperimentalMaterialApi::class, ExperimentalLifecycleComposeApi::class)
@Composable
fun PrevHome(
    bottomSheetState:ModalBottomSheetState,
    coroutineScope:CoroutineScope,
    carpoolListViewModel: HomeCarpoolListViewModel = viewModel()){
    val buttonState by carpoolListViewModel.carpoolExistState.collectAsStateWithLifecycle()
    carpoolListViewModel.getCarpoolTicket()
    Column() {
        HomeAppBar()
        Column(Modifier.padding(start = 16.dp, end = 16.dp,top=32.dp)) {
            HomeCardView(R.drawable.ic_home_folder,"공지사항",R.drawable.ic_home_rightarrow)
            Spacer(modifier = Modifier.height(4.dp))
            HomeCardView(R.drawable.ic_home_location,"지역설정",R.drawable.ic_home_rightarrow)
            Spacer(modifier = Modifier.height(4.dp))
            HomeCarpoolList()
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                coroutineScope.launch {
                    if(buttonState)
                        bottomSheetState.show()
                }
            },
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(),
            colors = if(!buttonState) ButtonDefaults.buttonColors(Color.Black)
                else ButtonDefaults.buttonColors(Colors.Blue_007AFF),
            shape = RoundedCornerShape(100.dp)
            ) {
                Text(
                    text = if(!buttonState) "생성한 카풀이 없습니다." else "내 카풀 보기",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W900,
                    color = Color.White
                )
            }
        }
    }

}

@Composable
fun HomeCardView(@DrawableRes image:Int,text:String,@DrawableRes icon:Int){
    ElevatedCard(shape = RoundedCornerShape(7.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
    colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(4.dp)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(id = image),
                contentDescription = null,
            modifier = Modifier
                .width(20.dp)
                .height(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color=Color.Black
                ,modifier = Modifier
                    .weight(1f)
                    .height(22.dp))

            IconButton(onClick = { /*TODO*/ }) {
                Icon(painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier
                        .width(24.dp)
                        .height(24.dp))
            }
        }
    }
}

@Composable
fun HomeCarpoolList(){
    Column(Modifier.height(370.dp)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(44.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(id = R.drawable.ic_home_list),
                contentDescription = null,
                Modifier
                    .width(24.dp)
                    .height(24.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "카풀 목록",
                modifier = Modifier.weight(1f))
            Image(painter = painterResource(id = R.drawable.ic_home_reddot),
                contentDescription = null)
            Text(text = "유료")
            Spacer(modifier = Modifier.width(6.dp))
            Image(painter = painterResource(id = R.drawable.ic_home_bluedot),
                contentDescription = null)
            Text(text = "무료")
        }
        Text(
            text = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Colors.Gray_DADDE1)
        )
        HomeCarpoolItems()
    }
}
@OptIn( ExperimentalLifecycleComposeApi::class, ExperimentalMaterialApi::class)
@Composable
fun HomeCarpoolItems(homeCarpoolListViewModel: HomeCarpoolListViewModel = viewModel()) {
    val carpoolList by homeCarpoolListViewModel.carpoolListState.collectAsStateWithLifecycle()
    homeCarpoolListViewModel.getCarpoolList()
    LazyColumn() {
        items(carpoolList) { item ->
            Column() {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    ProfileImage(image = R.drawable.icon_main_profile, 50.dp, 47.dp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Row(modifier = Modifier.weight(1f)) {
                        Text(
                            text = item.startArea!!,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = " 출발,",
                        )
                        Text(
                            text = when(item.dayStatus){
                                "MORNING"->"오전"
                                "EVENING"->"오후"
                                else->""
                            },
                        )
                        Text(
                            text = item.startTime!!,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Chip(
                        onClick = { /*TODO*/ },
                        colors = when (item.ticketType) {
                            "FREE" -> ChipDefaults.chipColors(Colors.Blue_007AFF)
                            "COST" -> ChipDefaults.chipColors(Colors.Red_E0302D)
                            else -> ChipDefaults.chipColors(Color.Gray)
                        }
                    )
                    {
                        Text(text = "${item.recruitPerson}/4")
                    }
                }
            }
            Text(
                text = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Colors.Gray_DADDE1)
            )
        }
    }
}


@OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalMaterialApi::class)
@Composable
fun HomeCarpoolSheet(carpoolListViewModel: HomeCarpoolListViewModel = viewModel()){
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val coroutineScope = rememberCoroutineScope()
    val ticketDetail by carpoolListViewModel.carpoolTicketState.collectAsStateWithLifecycle()

    ModalBottomSheetLayout(
        sheetContent = {
            Column(Modifier
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 16.dp,
                    bottom = 20.dp))
            {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp),
                    horizontalArrangement = Arrangement.End
                    ) {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            bottomSheetState.hide()
                    } })
                    {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_x),
                            contentDescription = null,
                            modifier = Modifier
                                .height(14.dp)
                                .width(14.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    Modifier
                        .height(56.dp)
                        .fillMaxWidth()) {
                    Column(
                        Modifier
                            .width(120.dp)
                            .fillMaxHeight()
                            .border(1.dp, Colors.Gray_A2ABB4, RoundedCornerShape(5.dp))
                            .padding(top = 8.dp, start = 12.dp, end = 12.dp, bottom = 8.dp)
                    )
                    {
                        Text(text = "출발지")
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = ticketDetail.startArea)
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Image(
                        painter = painterResource(id = R.drawable.ic_ticket_bluearrow), 
                        contentDescription = null,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Column(
                        Modifier
                            .width(120.dp)
                            .fillMaxHeight()
                            .border(1.dp, Colors.Gray_A2ABB4, RoundedCornerShape(5.dp))
                            .padding(top = 8.dp, start = 12.dp, end = 12.dp, bottom = 8.dp)
                    ) {
                        Text(text = "도착지")
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = ticketDetail.endArea)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    Modifier
                        .height(44.dp)
                        .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically)
                {
                    ProfileImage(image = R.drawable.icon_main_profile,50.dp,47.dp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(Modifier.weight(1f)) {
                        Text(text = "드라이버",
                            fontSize = 12.sp , color = Colors.Gray_4E5760,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = ticketDetail.memberName,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Image(painter = painterResource(id = R.drawable.ic_home_rightarrow),
                        contentDescription = null,
                        modifier = Modifier
                            .width(24.dp)
                            .height(24.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                HomeTicketDetail(
                    text1 = "출발 시간",
                    text2 = "${ticketDetail.startTime},${ticketDetail.startDayMonth}",
                    text3 = "탑승 장소",
                    text4 = ticketDetail.boardingPlace
                )
                Spacer(modifier = Modifier.height(20.dp))
                HomeTicketDetail(
                    text1 = "탑승 인원",
                    text2 = ticketDetail.recruitPerson.toString() + "명",
                    text3 = "비용",
                    text4 = ticketDetail.ticketType
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                colors = ButtonDefaults.buttonColors(Colors.Blue_007AFF),
                shape = RoundedCornerShape(100.dp)
                )
                {
                    Text(text = "탑승하기",
                        color = Color.White,
                        fontWeight = FontWeight.W900,
                        fontSize = 18.sp
                    )
                }
            }

    },
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(20.dp)
    ) {
        PrevHome(bottomSheetState,coroutineScope)
    }
}

@Composable
fun HomeTicketDetail(text1:String,text2:String,text3:String,text4:String){
    Row(
        Modifier
            .height(34.dp)
            .fillMaxWidth())
    {
        Column(Modifier
            .weight(1f))
        {
            Text(text = text1,
                Modifier
                    .fillMaxWidth()
                    .weight(1f))
            Text(text = text2,
                Modifier
                    .fillMaxWidth()
                    .weight(1f))
        }
        Column(Modifier
            .weight(1f))
        {
            Text(text = text3,
                Modifier
                    .fillMaxWidth()
                    .weight(1f))
            Text(
                text = when(text4){
                "FREE" -> "무료"
                "COST" -> "유료"
                else -> text4
            },
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
                color = when(text4){
                    "FREE" -> Colors.Blue_007AFF
                    "COST" -> Colors.Red_E0302D
                    else -> Color.Black
                }
            )
        }
    }
}
