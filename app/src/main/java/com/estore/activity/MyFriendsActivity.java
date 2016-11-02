package com.estore.activity;
/*
我的好友页面
 */

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.estore.R;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

public class MyFriendsActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private FragmentPagerAdapter mFragmentPagerAdapter;//将tab页面持久在内存中
    private Fragment mConversationList;
    private Fragment mConversationFragment = null;
    private List<Fragment> mFragment = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends);
        mConversationList=initConversationList();
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        mFragment.add(mConversationList);//加入会话列表


        //配置ViewPager的适配器
        mFragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            @Override
            public int getCount() {
                return mFragment.size();
            }
        };
        mViewPager.setAdapter(mFragmentPagerAdapter);


    }

    private Fragment initConversationList() {
        if(mConversationFragment==null) {
            ConversationListFragment listFragment = ConversationListFragment.getInstance();
            Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                    .appendPath("conversationList")
                    .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false")//设置私聊会话是否聚合显示
                    .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")
                    .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//设置私聊会话是否聚合显示
                    .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//设置私聊会是否聚合显示
                    .build();
            listFragment.setUri(uri);
            return listFragment;
        }else {
            return mConversationFragment;
        }
    }
}
