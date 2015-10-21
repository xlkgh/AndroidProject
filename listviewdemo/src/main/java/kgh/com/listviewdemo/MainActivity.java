package kgh.com.listviewdemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView listView;
    private ListView itemList;
    private List list=new ArrayList();
    private Button clickBtn;
    private Button clickBtn2;
    private SparseBooleanArray lastCheckedId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView= (ListView) findViewById(R.id.listview);
        clickBtn= (Button) findViewById(R.id.clickBtn);
        clickBtn2= (Button) findViewById(R.id.clickBtn2);
        for(int i=1;i<=20;i++) {
            list.add("选项"+i);
        }
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, list));
        clickBtn.setOnClickListener(this);
        clickBtn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.clickBtn:
                String selectItem="";
                SparseBooleanArray checkedId = listView.getCheckedItemPositions();
                for (int i = 0; i < checkedId.size(); i++) {
                    if(checkedId.valueAt(i)) {
                        selectItem += list.get(checkedId.keyAt(i))+",";
                    }
                }
                if("".equals(selectItem)){
                    Toast.makeText(MainActivity.this,"当前列表没有选中项", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this,"当前列表的选中项为:"+selectItem, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.clickBtn2:
                itemList=new ListView(MainActivity.this);
                itemList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                itemList.setAdapter(new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_multiple_choice, list));
                //恢复上次选中的记录
                if(null!=lastCheckedId) {
                    for (int i = 0; i < lastCheckedId.size(); i++) {
                        //先判断是否有这一选项
                        if (null != itemList.getItemAtPosition(lastCheckedId.keyAt(i))) {
                            //如果还有这一选项则恢复它选中的状态
                            itemList.setItemChecked(lastCheckedId.keyAt(i), lastCheckedId.valueAt(i));
                        }
                    }
                }

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("多选框 ")
                        .setView(itemList)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String selectItem="";
                                lastCheckedId = itemList.getCheckedItemPositions();
                                SparseBooleanArray checkedId = itemList.getCheckedItemPositions();
                                for (int i = 0; i < checkedId.size(); i++) {
                                    if(checkedId.valueAt(i)) {
                                        selectItem += list.get(checkedId.keyAt(i))+",";
                                    }
                                }
                                if("".equals(selectItem)){
                                    Toast.makeText(MainActivity.this,"弹出列表没有选中项", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(MainActivity.this,"弹出列表的选中项为:"+selectItem, Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
        }
    }
}
