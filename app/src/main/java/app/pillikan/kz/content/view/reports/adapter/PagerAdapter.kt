package app.pillikan.kz.content.view.reports.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import app.pillikan.kz.content.view.reports.AllReportsFragment
import app.pillikan.kz.content.view.reports.DeliveryReportsFragment
import app.pillikan.kz.content.view.reports.PayReportsFragment

class PagerAdapter(fm: FragmentManager, internal var totalTabs: Int) : FragmentStatePagerAdapter(fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> AllReportsFragment()
            1 -> PayReportsFragment()
            2 -> DeliveryReportsFragment()
            else -> getItem(position)
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }
}