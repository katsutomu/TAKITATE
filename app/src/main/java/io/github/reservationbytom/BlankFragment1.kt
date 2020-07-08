package io.github.reservationbytom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.facebook.FacebookSdk.getApplicationContext


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment1.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlankFragment1 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(
            R.layout.fragment_blank1,
            container,
            false
        )
        val db = SampleDBOpenHelper(getApplicationContext())
        val insertButton: Button = view.findViewById(R.id.insert)
        val insertText: EditText = view.findViewById(R.id.insertText)
        insertButton.setOnClickListener {
            val text : String = insertText.text.toString()
            insertText(getApplicationContext(),text)
        }
        val search: Button = view.findViewById(R.id.search)
        val showText: ListView = view.findViewById(R.id.showText)
        search.setOnClickListener {
            // ここに取得したやつをすべて表示したい
            val adapter = ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, selectData(getApplicationContext()))
            showText.adapter = adapter

        }


        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BlankFragment1.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BlankFragment1().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
