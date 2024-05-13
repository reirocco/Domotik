package com.example.domotik.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.ActionMode
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.domotik.R

class ImpostazioniMenu : AppCompatActivity(){

    private val impostazionimenu = arrayOf<String>("Impostazioni lingua","Sicurezza","Profilo", "Configurazione Dispositivi", "Info", "Centro Notifiche")
    var actionMode: ActionMode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.impostazioni)


        val arrayAdapter2: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, impostazionimenu)
        val listView2 = findViewById<ListView>(R.id.listView2)
        listView2.adapter = arrayAdapter2

        listView2.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> {
                    val intent = Intent(this, ImpostazioniLingua::class.java)
                    startActivity(intent)
                }

                2 -> {
                    //val selectedItem = listView2[position]
                    //if (selectedItem == "Profilo") {
                    // Apri il fragment_updateprofilo
                    val intent = Intent(this, ProfiloUtente::class.java)
                    startActivity(intent)
                }

            }}}}


        /*listView2.choiceMode = ListView.CHOICE_MODE_MULTIPLE_MODAL
        listView2.setMultiChoiceModeListener(object : AbsListView.MultiChoiceModeListener {
            override fun onItemCheckedStateChanged(
                mode: ActionMode, position: Int,
                id: Long, checked: Boolean
            ) {
                // Here you can do something when items are selected/de-selected,
                // such as update the title in the CAB
                mode.title = "${listView2.checkedItemCount} selezionati"
            }

            override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
                // Respond to clicks on the actions in the CAB
                return when (item.itemId) {
                    R.id.menu_share -> {
                        Toast.makeText(applicationContext, "Condividi item!", Toast.LENGTH_SHORT)
                            .show()
                        mode.finish() // Action picked, so close the CAB
                        true
                    }

                    else -> false
                }
            }

            override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
                // Inflate the menu for the CAB
                val menuInflater: MenuInflater = mode.menuInflater
                menuInflater.inflate(R.menu.context_menu, menu)
                return true
            }

            override fun onDestroyActionMode(mode: ActionMode) {
                // Here you can make any necessary updates to the activity when
                // the CAB is removed. By default, selected items are deselected/unchecked.
            }

            override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
                // Here you can perform updates to the CAB due to
                // an <code><a href="/reference/android/view/ActionMode.html#invalidate()">invalidate()</a></code> request
                return false
            }
        })
    }*/
    /*fun startMessagingActivity(view: View) {
        val intent = Intent(this, MessagingActivity::class.java)
        startActivity(intent)
    }*/


