package com.lovoo.ui.homelist.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lovoo.R
import com.lovoo.ui.lovoofactdetail.model.LovooFact
import com.lovoo.ui.homelist.model.OfficeRoom
import com.lovoo.network.NetError
import com.lovoo.network.NetworkProcessor
import com.lovoo.ui.base.BaseActivity
import com.lovoo.ui.lovoofactdetail.view.LovooFactActivity
import com.lovoo.utils.AppUtils
import kotlinx.android.synthetic.main.activity_home_list.*
import javax.inject.Inject
import android.app.SearchManager
import android.content.Context
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.lovoo.ui.homelist.viewmodel.OfficeRoomViewModel
import com.lovoo.ui.homelist.adapter.ListOfRoomAdapter
import com.lovoo.ui.meetingdetails.view.MeetingDetailActivity
import com.lovoo.utils.AppConstants

/**
 * Provides UI with @RecyclerView and Search functionality
 * Gets dependency injected from @BaseActivity
 * Differentiate b/w POJO with @LovooFact or Meeting Room
 * Handles @SwipeRefreshLayout
 *
 */
class HomeListActivity : BaseActivity(),  View.OnClickListener,
                        SwipeRefreshLayout.OnRefreshListener , HomeListActions
{
    @Inject
    lateinit var mAppUtils : AppUtils
    @Inject
    lateinit var mNetwork : NetworkProcessor
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mRoomAdapter: ListOfRoomAdapter
    lateinit var officeListViewModel: OfficeRoomViewModel
    private val TAG:String = HomeListActivity::class.java.toString()
    private lateinit var searchView: SearchView
    private lateinit var mSwipeRefreshLayout : SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        getInjectionComponent().inject(this)
        super.onCreate(savedInstanceState)
        renderView()
        init()
    }

    override fun renderView() {
        setContentView(R.layout.activity_home_list)
    }

    override fun init() {
        setSupportActionBar(toolbar)
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh)
        mSwipeRefreshLayout.setOnRefreshListener {
            onRefresh()
        }
        mRecyclerView = findViewById(R.id.listOfRoom)
        mRecyclerView.layoutManager = LinearLayoutManager(this)

        officeListViewModel= ViewModelProviders.of(this).get(OfficeRoomViewModel::class.java)
        officeListViewModel.mNetworkProcessor = mNetwork
        officeListViewModel.mAppUtils = this.mAppUtils
        officeListViewModel.homeListActivity =this
        officeListViewModel.officeData.observe(this@HomeListActivity, Observer {
                officeRoomData ->
            mRoomAdapter = ListOfRoomAdapter(officeRoomData, this)
            mRecyclerView.adapter = mRoomAdapter
        })
    }

    override fun onClick(v: View?) {
        var selectedItem : OfficeRoom =v?.getTag() as OfficeRoom
        var lovooFACT: LovooFact?=  selectedItem.lovooFact
        lovooFACT?.let {
            //lovoofact is present, invoke details
            var intent= Intent(this, LovooFactActivity::class.java)
            intent.putExtra(AppConstants.LOVOO_FACT,selectedItem.lovooFact)
            intent.putExtra(AppConstants.OFFICE_LEVEL,"${selectedItem.officeLevel}")
            intent.putExtra(AppConstants.ROOM_NUMBER,selectedItem.roomNumber)
            startActivity(intent)
        } ?: run{
            //For meeting show detailed meeting
            if(selectedItem.type.equals(AppConstants.MEETING_TAG,true)){
                var intent= Intent(this, MeetingDetailActivity::class.java)
                intent.putExtra(AppConstants.DATA,selectedItem)
                startActivity(intent)
            }else{
                showToast(getString(R.string.no_details_to_show))
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu,menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu?.findItem(R.id.action_search)
            ?.actionView as SearchView
        searchView.setSearchableInfo(searchManager
            .getSearchableInfo(getComponentName()));
        searchView.maxWidth = Integer.MAX_VALUE;

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // filter recycler view when query submitted
                mRoomAdapter.getFilter().filter(query)
                return false
            }
            override fun onQueryTextChange(query: String): Boolean {
                // filter recycler view when text is changed
                mRoomAdapter.getFilter().filter(query)
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.getItemId()
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    override fun onRefresh() {
        setRefreshingVisible()
        officeListViewModel.loadData()
    }

    fun onError(networkError: NetError) {
        setRefreshingGone()
        showAlert(networkError.errorMsg,R.string.retry,R.string.cancel_option)
    }

    override fun showNetworkError(){
        setRefreshingGone()
        showAlert(getString(R.string.internet_error),R.string.retry,R.string.cancel_option)
    }

    override fun setRefreshingVisible(){
        mSwipeRefreshLayout.isRefreshing = true
    }

    override fun setRefreshingGone(){
        mSwipeRefreshLayout.isRefreshing = false
    }

    override fun handleNegativeAlertCallBack() {
    }

    override fun handlePositiveAlertCallBack() {
        onRefresh()
    }
}
