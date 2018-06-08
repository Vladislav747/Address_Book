package com.example.melo.adress_book;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * Created by Melo on 07.06.2018.
 */

//Расширяет класс ListFragment для вывода списка контактов в компонете ListView, а также
    //предоставляет команду меню
    //для добавления нового контакта



    //Класс ListFragment используется потому что даннные отображаются в форме списка(List)
public class ContactListFragment extends ListFragment {
    public void updateContactList() {

    }

    // callback methods implemented by MainActivity
    public interface ContactListFragmentListener {
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
// устанавливаем режим выбора пунктов списка  - выбор только один
        contactListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        //Имя контакта связывается с TextView в макете ListView;
        String[] from = new String[]{"name"};
        int[] to = new int[]{android.R.id.text1};
        //Связь с базой данных
        //Здесь используется перменные from и to
        //Субкласс SimpleCursorAdapter упрощающий связывание столбцы Cursor с компонентами
        //TextView и ImagesView
        //До этого массивы мы получили в переменных выше.
        //В этом объекте с layoutом  simple_list_item_1
        contactAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_1,
                null, from, to, 0); //Первый аргумент - это контекст, Третий аргумент - объект
        //Cursor доступ к данным - передается null потому что объект передан позднее.,

//Адаптер, поставляющий данные
        //Устанавливаем этот адаптер
        setListAdapter(contactAdapter);
    }

    //Слушатель viewContactListener оповещает MainActivity о том, что пользователь коснулся контакта
    //чтобы вывести подробную инфу В строке id передается методу  onContactSelected слушателя
    AdapterView.OnItemClickListener viewContactListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            listener.onContactSelected(id);//Выбранный элемент передается MainActivity
        }
    };


    //При возобновлении фрагмента задача GetContactsTask загружает контакты
    @Override
    public void onResume() {
        super.onResume();
new GetContactsTask.execute(Object[]) null);
    }


    //Выполнение запроса к базе данных вне потока GUI
    private class GetContactsTask extends AsyncTask {
DatabaseConnector databaseConnector = new DatabaseConnector(getActivity());

        @Override
        protected Object doInBackground(Object[] objects) {
            databaseConnector.open();
            return databaseConnector.getAllContacts();
        }

        @Override
        protected void onPostExecute(Object o) {
            //Назначение курсора для адаптера
            contactAdapter.changeCursor(o);
            super.onPostExecute(o);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    //Обновление набора данных
    public void updateContactList(){

        new GetContactsTask().execute((Object[] null);
    }
}

