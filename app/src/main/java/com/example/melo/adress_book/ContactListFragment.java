package com.example.melo.adress_book;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;

/**
 * Created by Melo on 07.06.2018.
 */

//Расширяет класс ListFragment для вывода списка контактов в компонете ListView, а также
    //предоставляет команду меню
    //для добавления нового контакта
public class ContactListFragment extends ListFragment{
    public void updateContactList() {

    }

    // callback methods implemented by MainActivity
    public interface ContactListFragmentListener
    {
        // called when user selects a contact
        public void onContactSelected(long rowID);

        // called when user decides to add a contact
        public void onAddContact();
    }
//Перменная экземпляра listener, кот-я будет ссылаться на объект MainActivity, реализующий интерфейс

    private ContactListFragmentListener listener;

/* Перменная экземпляра  contactListView содержит ссылку на компонент ContactListFragment
встроенный в ListView, по этой ссылке мы сможем взаимодействовать с ним*/
    private ListView contactListView;
    /*Переменная contactAdapter содержит ссылку на объект CursorAdapter,
    * заполняющий компонент ListView приложения*/
    //Адаптер ListView
    //Для работы с базой даных
    private CursorAdapter contactAdapter;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //Передаем управление Activity переменной listener
        listener = (ContactListFragmentListener) activity;
    }

    @Override
    //Прерываем управление при отсоедение фрагмента

    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       /* А чтобы при пересоздании сохранить сам объект класса Fragment, используйте метод
       setRetainInstance. Если передать в него true, то при пересоздании фрагмента не буду
        вызваны методы onDestroy и onCreate, и не будет создан новый экземпляр класса Fragment.
        */
        setRetainInstance(true); //Сохранение между изменениями конфигурации

        //Report that this fragment would like to participate
        // in populating the options menu by receiving a
        // call to onCreateOptionsMenu(Menu, MenuInflater) and related methods.
        setHasOptionsMenu(true); //У фрагмента есть команды меню

        //Текст отображаемыый при отсутствии контактов
        //Ссылка на элемент String - no_contacts
        setEmptyText(getResources().getString(R.string.no_contacts));

        //Получение ссылки на ListView и настройка ListView
        contactListView = getListView();
        //Назначаем обработчка на пункты меню
        contactListView.setOnItemClickListener(viewContactListener);
// устанавливаем режим выбора пунктов списка  - выбор только одинарно
        contactListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        //Имя контакта связывается с TextView в макете ListView;
String[] from = new String[] {"name"};
int[] to = new int[] {android.R.id.text1};
    }
}

