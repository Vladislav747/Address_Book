package com.example.melo.adress_book;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity implements ContactListFragment.ContactListFragmentListener,
        DetailsFragment.DetailsFragmentListener,
        AddEditFragment.AddEditFragmentListener{


    //Ключи идентификатора строки в объекте Bundle, передаваемых фрагменту
    public static final String ROW_ID = "row_id";

    ContactListFragment contactListFragment = new ContactListFragment(); //вывод списка контактов

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Если активность восстанавливается, просто вернуть управление
        //заново создавать GUI не нужно
        if(savedInstanceState != null)
            return;

        //Проверить содержит ли макет fragmentContainer (макет для телефона)
        //ContactListFragment отображается всегда
        if (findViewById(R.id.fragmentContainer) != null){
            //Создание ContactListFragment
            contactListFragment = new ContactListFragment();

            //Добавление фрагмента в FrameLayout
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.fragmentContainer, contactListFragment);
            transaction.commit();
        }

    }

    // called when MainActivity resumes
    @Override
    protected void onResume()
    {
        super.onResume();

        // if contactListFragment is null, activity running on tablet,
        // so get reference from FragmentManager
        if (contactListFragment == null)
        {
            contactListFragment =
                    (ContactListFragment) getFragmentManager().findFragmentById(
                            R.id.contactListFragment);
        }
    }



    //метод вызывается из интерфейса ContactListFragment.ContactListFragmentListener
    // display DetailsFragment for selected contact
    //В заисимости от типа устройства планшет или телефон мы используем следующие условия
    //если телефон то заполняется фрагментом контейнер в ином случае фрагмент ставится справа от центра
    @Override
    public void onContactSelected(long rowID)
    {
        if (findViewById(R.id.fragmentContainer) != null) // phone
            displayContact(rowID, R.id.fragmentContainer);
        else // tablet
        {
            getFragmentManager().popBackStack(); // removes top of back stack
            displayContact(rowID, R.id.rightPaneContainer);
        }
    }

    //Метод из интерфейса ContactListFragment.ContactListFragmentListener
    //отображение фрагмента AddeditFragment для добавления контакта
    @Override
    public void onAddContact() {
        //Check that element is not empty
        //Phone
if(findViewById(R.id.fragmentContainer) != null)
    displayAddEditFragment(R.id.fragmentContainer, null);
else
    //Планшет
        displayAddEditFragment(R.id.rightPaneContainer,null);
    }


    //Отображение информации о контакте
    private void displayContact(long rowID, int viewID){
        DetailsFragment detailsFragment = new DetailsFragment();

        //передача rowID в аргументе DetailsFragment
        //Bundle необходим для временного хранения данных в процессе выполнения.Это отличный выбор
        // при передаче данных между активностями. Это способ для сохранения данных
        // при смене ориентации экрана.
       // Вообщем это сохранённые данные, которые система для использует для
        // восстановления предыдущего состояния. Представляет собой набор пар ключ-значение.

        Bundle arguments = new Bundle();
//проверяю что arguments не пустой
if(arguments == null){
    throw new IllegalArgumentException("Ошибка с Bundle аргументом он пустой!");
}

        arguments.putLong(ROW_ID, rowID);
        detailsFragment.setArguments(arguments);

        //Использование FragmentTransaction для отображения DetailsFragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(viewID, detailsFragment);
        //Невозможно вытащить из backstack
        transaction.addToBackStack(null);
        transaction.commit(); // приводит к отображению DetailsFragment

    }


    //Отображение фрагмента для изменения или добавления контакта
    private void displayAddEditFragment(int viewID, Bundle arguments){
        AddEditFragment addEditFragment = new AddEditFragment();
        //Если аргументы не отрицательны
if (arguments != null){
    addEditFragment.setArguments(arguments);

    //Использование FragmentTransaction для отображения AddEditFragment
    FragmentTransaction transaction = getFragmentManager().beginTransaction();
    transaction.replace(viewID, addEditFragment);
    transaction.addToBackStack(null);
    transaction.commit();

}

    }

    //Влзврат к списку контактов  после удаления
    private void onContactDeleted(){
        //извлекает верхний элемент из стэка
        getFragmentManager().popBackStack();
        if (findViewById(R.id.fragmentContainer)== null){
            contactListFragment.updateContactList();
        }
    }


    //Отображение AddEditFragment для изменения существующего объекта
    @Override
    public void onEditContact(Bundle arguments){


    }












}
