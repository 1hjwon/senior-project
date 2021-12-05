package com.example.last.ui.home

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.WindowManager
import android.widget.*
import androidx.lifecycle.MutableLiveData
import com.example.last.R
import com.example.last.ROOM.schedule.schedule
import com.example.last.databinding.ScheduleinsertdialogBinding
import com.example.last.databinding.SchedulerudlayoutBinding

class customDialog(context: Context, date:String, viewModel: HomeViewModel) {
    private val idialog = Dialog(context)
    private val ruddialog = Dialog(context)
    private val completeDialog = Dialog(context)

    //private lateinit var onClickListener: ButtonClickListener
    private val date = date
    private val viewModel = viewModel
    private val context = context

    /*interface ButtonClickListener{
        fun onClick(time: String)
    }*/

    lateinit var binding:SchedulerudlayoutBinding

    //private val item: schedule
    fun setItem(){
        //item =
    }

    var _temp = ArrayList<schedule>()

    fun showRUDDialog(arrayList: ArrayList<schedule>){
        binding = SchedulerudlayoutBinding.inflate(ruddialog.layoutInflater)
        ruddialog.setContentView(binding.root)
        ruddialog.setCancelable(true)


        val items = arrayList
        //Log.i("생성자 넘기기", items.toString()) // o
        val temp = ArrayList<schedule>()

        for (item in items) {
            if(item.date == date){
                temp.add(item)
                break
            }
        }

        _temp = temp

        


        //Log.i("temp! 임시변수 넣어졌나요!! ", temp.toString())

        // 1. 출력 - 완료
        binding.rSchedTitle.text = temp.first().title//어떻게 넣지?
        binding.rSchedContent.text = temp.first().content
        //Log.i("출력단 temp", temp.toString())

        binding.ratingText.text = temp.first().rating.toString() //> ok

/*
        binding.completeRating.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            binding.completeRating.rating = 3f //될까요?! 되면 db에서 바로 주입; 기본 0

        }*/
        //수정 삭제
        // 2. 수정
        val modScheduleBinding = ScheduleinsertdialogBinding.inflate(idialog.layoutInflater)
        idialog.setContentView(modScheduleBinding.root)

        val tDate = temp.first().date
        val tTitle = temp.first().title
        val tContent = temp.first().content

        binding.schedulemodimage.setOnClickListener {
            //수정
            //레이아웃만들어서 팝업시키고 삽입(날짜같은곳에삽입하면덧씌워짐)
            idialog.show()

            modScheduleBinding.insertButton.text = "Modify"

            modScheduleBinding.dialogContent.hint = temp.first().content
            modScheduleBinding.dialogTitle.hint = temp.first().title

            modScheduleBinding.insertButton.setOnClickListener {
                if (modScheduleBinding.dialogTitle.text.isNullOrEmpty() ||
                    modScheduleBinding.dialogContent.text.isNullOrEmpty()){
                    Toast.makeText(this.context, "내용을 입력해주세요", Toast.LENGTH_SHORT).show()


                } else {
                    val mTitle = modScheduleBinding.dialogTitle.text.toString()
                    val mContent = modScheduleBinding.dialogContent.text.toString()

                    val schedule = schedule(tDate, mTitle, mContent)
                    viewModel.update(schedule)


                    Toast.makeText(this.context, "수정되었습니다 ", Toast.LENGTH_SHORT).show()
                    binding.rSchedTitle.text = schedule.title//어떻게 넣지?
                    binding.rSchedContent.text = schedule.content

                    Log.i("수정단 schedule", schedule.toString())
                    Log.i("수정단 temp", temp.toString())

                    idialog.dismiss()
                }

            }

            modScheduleBinding.cancelButton.setOnClickListener {
                modScheduleBinding.dialogTitle.text.clear()
                modScheduleBinding.dialogContent.text.clear()

                idialog.dismiss()
            }


        }
        val dToast = Toast.makeText(this.context, "삭제되었습니다", Toast.LENGTH_SHORT)
        // 3. 삭제
        binding.scheduledeleteImage.setOnClickListener {
            //삭제
            dToast.show()
            //레이아웃만들어서 팝업시키고 확인물어보기
            viewModel.delete(temp.first())
            ruddialog.dismiss()
        }

        binding.cancelSchedButton.setOnClickListener {
            ruddialog.dismiss() //취소버튼
        }



        binding.completeSchedButton.setOnClickListener {
            // 4.1 완성도 설정; 별 레이팅으로 관리
            //                    val mTitle = modScheduleBinding.dialogTitle.text.toString()

            val rate = MutableLiveData<String>().apply {
                value = temp.first().rating.toString()
            }
            binding.ratingText.text = rate.value.toString()
            Log.i("rate", rate.toString())


            complete()
            //completeDialog.show()
            //ruddialog.dismiss()
            //ruddialog.show()

        }

        ruddialog.show()
    }
    fun complete(){
        completeDialog.setContentView(R.layout.schedulecomplete)
        completeDialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        completeDialog.setCancelable(true)

        binding = SchedulerudlayoutBinding.inflate(ruddialog.layoutInflater)


        val cancel = completeDialog.findViewById<Button>(R.id.cancel_completeButton)
        val done = completeDialog.findViewById<Button>(R.id.done_rating)
        val rating = completeDialog.findViewById<RatingBar>(R.id.insertRating)
        //var rateValue:Int

        cancel.setOnClickListener {
            completeDialog.dismiss()
        }

        done.setOnClickListener {
            //레이팅 숫자 삽입 후 삽입; 쿼리문에 넣는거 만들기 -> 이후 정보나오는창에서 나오게만들기
            Log.i("rating??", rating.rating.toInt().toString())
            val item = _temp.first()

            viewModel.update(schedule(item.date, item.title, item.content, rating.rating) )

            //binding.completeRating.rating = rating.rating
            binding.ratingText.text = rating.rating.toString()





            completeDialog.dismiss()

            ruddialog.dismiss()

            ruddialog.show()


        }


        completeDialog.show()

    }

    fun showInsertDialog() {
        idialog.setContentView(R.layout.scheduleinsertdialog)
        idialog.window!!.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        idialog.setCancelable(true)

        val title = idialog.findViewById<EditText>(R.id.dialogTitle)
        val content = idialog.findViewById<EditText>(R.id.dialogContent)
        val cancel = idialog.findViewById<Button>(R.id.cancelButton)
        val done = idialog.findViewById<Button>(R.id.insertButton)

        //val schedule = schedule(date, title.text.toString(), content.text.toString())

        cancel.setOnClickListener {
            idialog.dismiss()
        }

        done.setOnClickListener {
            viewModel.insert(schedule(date, title.text.toString(), content.text.toString()))
            Toast.makeText(context, "저장되었습니다", Toast.LENGTH_SHORT).show()
            idialog.dismiss()
        }


        idialog.show()
    }
}