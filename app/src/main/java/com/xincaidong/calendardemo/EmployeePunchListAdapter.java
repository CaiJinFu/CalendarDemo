package com.xincaidong.calendardemo;

import android.text.TextUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

public class EmployeePunchListAdapter extends BaseQuickAdapter<DateEntity, BaseViewHolder> {

  public EmployeePunchListAdapter() {
    super(R.layout.recycler_item_employee_punch_list);
  }

  @Override
  protected void convert(BaseViewHolder helper, DateEntity item) {
    if (!TextUtils.isEmpty(item.day)) {
      if (item.day.startsWith("0")) {
        String replace = item.day.replace("0", "");
        helper.setText(R.id.tv, replace);
        return;
      }
    }
    helper.setText(R.id.tv, item.day);
  }
}
