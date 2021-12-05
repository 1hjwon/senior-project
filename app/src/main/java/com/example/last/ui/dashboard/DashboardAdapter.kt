package com.example.last.ui.dashboard

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.last.R
import com.example.last.ROOM.memo.memo
import com.example.last.databinding.FragmentDashboardBinding

//https://android-dev.tistory.com/59 ERV
//https://todaycode.tistory.com/26 VP2
// android:clipToPadding="false"
//, dashboardFragment: DashboardFragment
class DashboardAdapter(context: Context, binding: FragmentDashboardBinding, viewModel: DashboardViewModel)
    : RecyclerView.Adapter<DashboardAdapter.viewHolder1>(), Filterable//context: Context
{
    private var items = ArrayList<memo>()
    private var filterItems = ArrayList<memo>()

    private val bind:FragmentDashboardBinding = binding
    private val context = context
    private val viewModel = viewModel
    //private var ItemBinding: MemoitemBinding
    //private lateinit var items: List<memo>
    //val items:List<memo> = items.
    //, val memoItems: ArrayList<memo>

    //private val autoCompleteTextView = bind.importdashboard.memoEditText

    fun setfilter(filteredList: ArrayList<memo> ){
        filterItems = filteredList;
        notifyDataSetChanged()
    }

    fun getItems(): ArrayList<memo>{
        return this.items
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var autoTV = bind.importdashboard.memoEditText
                var chars = constraint.toString()
                //autoTV.text.filters = chars.toString()
                Log.i("검색;chars", chars.toString()) //o

                filterItems = if (chars.isEmpty()){
                    Log.i("검색;items", items.toString()) //o

                    items

                } else {
                    val filtered = ArrayList<memo>()
                    for (item in items){
                        if(item.Title!!.contains(chars) ){ //|| item.Content == chars , 여기서 문자열 파싱
                            filtered.add(item)
                            Log.i("검색;filtered", filtered.toString()) //x

                        }

                    }

                    filtered

                }
                Log.i("검색;filterItems(out)", filterItems.toString()) // 완전히 같아야 하는듯... -> 파싱?



/*
                if (chars.isEmpty()) {
                    filterItems = items
                    Log.i("검색;items", items.toString())
                    Log.i("검색;filterItem(empty)", filterItems.toString())

                } else {
                    var filtered = ArrayList<memo>()
                    Log.i("검색;filtered", filtered.toString()) //x

                    for(item in items){
                        if(item.Title ==  chars || item.Content == chars) {
                            filtered.add(item)
                            Log.i("검색;filtered", filtered.toString()) //x

                        }

                    }
                    filterItems = filtered
                    Log.i("검색;filterItems(out)", filterItems.toString()) //x

                }

                */
                val filteredResult = FilterResults()
                Log.i("검색;filterResult", filteredResult.toString())//x -> 당연히없지..

                filteredResult.values = filterItems
                Log.i("검색;filterItems(after)", filterItems.toString()) //o

                Log.i("검색;return", filteredResult.values.toString())//값은 들어감
                Log.i("검색;return", filteredResult.toString())// 이거안댐.. 아마 겉에 그 그거라 그런듯; 근ㄷ ㅔ왜 안나오지? 안에 있는 값들은 나와야하는거아닌가

                return filteredResult



            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterItems = results?.values as ArrayList<memo>//여기서 값을 뿌려주지 못함 -> 아무리 생각해도 .... 보여주는데서 item을 직접 보여줌.....
                Log.i("검색;publishResults", filterItems.toString())
                notifyDataSetChanged()
            }
        }

    } //??

    inner class viewHolder1(view: View): RecyclerView.ViewHolder(view){
        //val memo =
       //var binding1: MemoitemBinding = binding
        var items: ArrayList<memo> = this@DashboardAdapter.items
        val title = view.findViewById<TextView>(R.id.memotitletextview)
        //val content = view.findViewById<TextView>(R.id.expandTV)
        val time = view.findViewById<TextView>(R.id.memoitem_time)//view.findViewById<TextView>(R.id.expTime) --> 따로 출력
        val date = view.findViewById<TextView>(R.id.memoitem_date)

        val image = view.findViewById<ImageView>(R.id.openarrow1)
        val exp = view.findViewById<LinearLayout>(R.id.constITEM)



       // val button = view.findViewById<Button>(R.id.insertMemoButton)
        //val REQUESTCODE = 1111
        //private lateinit var getResult: ActivityResultLauncher<Intent>

        //val binding: MemoitemBinding =
        @RequiresApi(Build.VERSION_CODES.O)
        fun bindVH(memo: memo, position: Int){ //-> 149

            title.text = memo.Title
            //content.text = memo.Content
            date.text = memo.Date
            time.text = memo.Time

            //var isClicked: Boolean = memo.Click
            //val pos = getitem
            val dialog1 = customDialog(context, this@DashboardAdapter.viewModel)
            val dialog2 = customDialog(context, this@DashboardAdapter.viewModel)
            exp.setOnClickListener {
                //val t1 = Toast.makeText(this@DashboardAdapter.context, "레이아웃!!", Toast.LENGTH_SHORT)
                //t1.show()
                //인텐트 -> 어댑터에서 못함
                //수정삽입은 팝업시키고 내용 보는건 인텐트로
                //인텐트로 값을을 넣어준 후
                //수정삽입 레이아웃은 재활용

                dialog1.showDRialog(items[position])


/*
                //getResult =
                   // registerAdapterDataObserver(ActivityResultContracts.StartActivityForResult())
                //val intent = Intent( context , DashboardIntent::class.java)
                //intent.putExtra("test", "test")
                //getResult = registerAdapterDataObserver()
                //val resultLauncher = registerForAc
               //ActivityResultContracts.StartActivityForResult(intent, REQUESTCODE, )


                ////val exp = toggle(true, image, exp) //아니면? -> 그냥안함
                //뷰추가하기 layoutExpand: LinearLayout) 기존 뷰 -> 이미지뷰, 레이아웃 추가
                // Attempt to invoke virtual method 'void android.widget.LinearLayout.measure(int, int)' on a null object reference
                //Log.i("exp? ", exp.toString()) //1
                //isClicked = exp
                //Log.i("is Clicked? ", isClicked.toString()) //1
*/
            }

            //image.setImageResource(R.drawable.ic_baseline_restore_from_trash_24)
            image.setOnClickListener {
                viewModel.delete(items[position])
                Toast.makeText(context, "삭제되었습니다", Toast.LENGTH_SHORT).show()

            }
/*
            button.setOnClickListener {
                dialog2.showIMDialog()
            }
*/
            //버튼생성 후 리스너
            //생성 -> 인텐트/팝업

            /*
            title.setOnClickListener {
                val t1 = Toast.makeText(this@DashboardAdapter.context, "!", Toast.LENGTH_SHORT)
                //t1.show()
                //구조 바꾸고 적용
                //val  exp = toggle(true, bind)
            }*/

            /*
            image.setOnClickListener {
                //val exp = toggle(true, binding )
                //isClicked = exp
            }
            */

            //val view = view
 /*
            val mTitle = view binding.memotitletextview
            val constLayout = binding.constITEM
            val arrow = binding.openarrow1 //
            val expandable = binding.mExpandable //



            mTitle.text = memo.Title
            var isClicked: Boolean = memo.Click
            //binding.mExpandable
            //const
            //constLayout.setOnClickListener {
                //val exp = toggle
            //}
            binding.constITEM.setOnClickListener {


            }
*/

        }

        fun toggle(isExpanded: Boolean, view: ImageView, linearLayout: LinearLayout): Boolean{
            ToggleAnimation.toggleArrow(view, isExpanded)
            if(isExpanded){
                ToggleAnimation.expand(linearLayout)
            } else {
                ToggleAnimation.collapse(linearLayout)
            }
            return isExpanded
        }
        /*
                fun toggle(isExpanded: Boolean, binding: MemoitemBinding): Boolean{
            ToggleAnimation.toggleArrow(binding, isExpanded)
            if(isExpanded){
                ToggleAnimation.expand(binding)
            } else {
                ToggleAnimation.collapse(binding)
            }
            return isExpanded
        }
         */
    }

    fun setMemo(items: List<memo>){
        this.items.clear()
        this.items.addAll(items)
    }

    //아이템개수반환
    override fun getItemCount(): Int {

        return if (filterItems.isEmpty()){
            items.size
        } else {
            filterItems.size
        }
        //items.size
    }

    // 생성된 View에 보여줄 데이터를 설정
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: viewHolder1, position: Int) { //
        val memo = items[position]
        val memoholder = holder as viewHolder1


        if(filterItems.isEmpty()){
            memoholder.bindVH(memo, position)

        } else {
            memoholder.bindVH(filterItems[position], position)

        }

        //holder.items = this.items ???

        //bindViewHolder(holder, position)
    }

    //보여줄 아이템 개수만큼 View 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder1 {
        //val bind:MemoitemBinding
        val view = LayoutInflater.from(context).inflate(R.layout.memoitem, parent, false)
        val viewHolder = viewHolder1(view)
        //val bind = MemoitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.memoitem, parent, false)

        return viewHolder
    }

}


//안씀
class ToggleAnimation(){
    companion object {
        fun toggleArrow(view: View, isExpanded: Boolean): Boolean {
            val _arrow = view.findViewById<ImageView>(R.id.openarrow1)
            if (isExpanded) {
                _arrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_left_24)
                return true
            } else {
                _arrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                return false
            }
        }

        /*
                fun toggleArrow(binding: MemoitemBinding, isExpanded: Boolean): Boolean {
            val _arrow = binding.openarrow1
            if (isExpanded) {
                _arrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_left_24)
                return true
            } else {
                _arrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                return false
            }
        }
         */
        fun expAction(view: View): Animation{
            val _Exp = view //view!!.findViewById<LinearLayout>(R.id.mExpandable)
            _Exp.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            val height = _Exp.measuredHeight

            _Exp.layoutParams.height = 0
            _Exp.visibility = View.VISIBLE

            val animation = object : Animation() {
                override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                    _Exp.layoutParams.height = if (interpolatedTime == 1f)
                                                    { ViewGroup.LayoutParams.WRAP_CONTENT }
                    else (height * interpolatedTime).toInt()

                    _Exp.requestLayout()
                }
            }

            //density : 디스플레이의 논리적 밀도입니다. 이것은 Density Independent Pixel 단위에 대한 배율 요소입니다.
            animation.duration = (height / _Exp.context.resources.displayMetrics.density).toLong()
            _Exp.startAnimation(animation)
            return animation
        }

        /*
                fun expAction(binding: MemoitemBinding): Animation{
            val _Exp = binding.mExpandable
            _Exp.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            val height = binding.mExpandable.measuredHeight

            _Exp.layoutParams.height = 0
            _Exp.visibility = View.VISIBLE

            val animation = object : Animation() {
                override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                    _Exp.layoutParams.height = if (interpolatedTime == 1f)
                                                    { ViewGroup.LayoutParams.WRAP_CONTENT }
                    else (height * interpolatedTime).toInt()

                    _Exp.requestLayout()
                }
            }

            //density : 디스플레이의 논리적 밀도입니다. 이것은 Density Independent Pixel 단위에 대한 배율 요소입니다.
            animation.duration = (height / _Exp.context.resources.displayMetrics.density).toLong()
            _Exp.startAnimation(animation)
            return animation
        }
         */
        fun expand(view: View){ //        fun expand(binding: MemoitemBinding){ val animation = expAction(binding) binding.mExpandable.startAnimation(animation) }

            val animation = expAction(view)
            view.startAnimation(animation)
            //view.findViewById<LinearLayout>(R.id.)
            //view.findViewById<LinearLayout>(R.id.mExpandable).startAnimation(animation)

        }
        /*
        fun expand(binding: MemoitemBinding){
            val animation = expAction(binding)
            binding.mExpandable.startAnimation(animation)
        }

         */
        fun collapse(view: View) {//      fun collapse(binding: MemoitemBinding) {
            //val _Exp = view.findViewById<LinearLayout>(R.id.mExpandable)
            val height = view.measuredHeight//_Exp.measuredHeight

            /*
            애니메이션 시작 interpolatedTime = 0.0f
            애니메이션 중간 interpolatedTime = 0.5f
            애니메이션 끝 interpolatedTime = 1.0f
             */
            val animation = object : Animation(){
                override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                    if(interpolatedTime == 1f)
                        view.visibility = View.GONE//_Exp.visibility = View.GONE
                    else {
                        //_Exp.layoutParams.height
                            view.layoutParams.height = (height - (height * interpolatedTime)).toInt()
                        view.requestLayout() //_Exp.requestLayout()
                    }
                }
            }

            animation.duration = (height / view.context.resources.displayMetrics.density).toLong()
            view.startAnimation(animation)
        }

        /*
            fun collapse(binding: MemoitemBinding) {
            val _Exp = binding.mExpandable
            val height = _Exp.measuredHeight

            /*
            애니메이션 시작 interpolatedTime = 0.0f
            애니메이션 중간 interpolatedTime = 0.5f
            애니메이션 끝 interpolatedTime = 1.0f
             */
            val animation = object : Animation(){
                override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                    if(interpolatedTime == 1f)
                        _Exp.visibility = View.GONE
                    else {
                        _Exp.layoutParams.height = (height - (height * interpolatedTime)).toInt()
                        _Exp.requestLayout()
                    }
                }
            }

            animation.duration = (height / _Exp.context.resources.displayMetrics.density).toLong()
            _Exp.startAnimation(animation)
        }
        */


    }
}