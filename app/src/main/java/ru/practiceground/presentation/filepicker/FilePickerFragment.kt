package ru.practiceground.presentation.filepicker

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.dialog_compress.view.*
import ru.practiceground.R
import ru.practiceground.databinding.FragmentFilePickerBinding
import ru.practiceground.other.getBinding
import ru.practiceground.presentation.base.BaseFragment

private const val REQUEST_CODE = 123

class FilePickerFragment : BaseFragment() {
    override val viewModel: FilePickerViewModel by viewModels()
    override val bgDrawable: Drawable? = defaultBgColor
    private lateinit var binding: FragmentFilePickerBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = getBinding(container, R.layout.fragment_file_picker)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
    }

    private fun subscribe() {
        with(viewModel) {
            openFilePicker.setUnitObserver(::startFilePicker)
            fileUri.setObserver {
                Glide.with(this@FilePickerFragment).run {
                    if (it == null)
                        load(R.drawable.ic_round_image_24)
                    else
                        load(it)
                }.into(binding.imageIv)
            }
            openPickSizeDialog.setObserver(::openSizeDialog)
        }
    }

    private fun openSizeDialog(size: Int) {
        var sizeTo = size
        val view = layoutInflater.inflate(R.layout.dialog_compress, null)
        view.seek_value.text = "0"
        view.dialog_seek_bar.apply {
            max = size
            setOnSeekBarChangeListener(object  : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(sB: SeekBar?, progress: Int, fromUser: Boolean) {
                    sizeTo = progress
                    view.seek_value.text = progress.toString()
                }
                override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
                override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
            })
        }
        AlertDialog.Builder(context ?: return).apply {
            setTitle("Выберите размер файла")
            setMessage("Избражение будет сжиматься по 5% процентов, пока не будет меньше указанного размера или не сожмется до минимума")
            setView(view)
            setPositiveButton("Сжать") { _, _ -> viewModel.onDialogOkClick(sizeTo * 1000) }
            setNegativeButton("Отмена") { _, _ ->  }
        }.show()
    }

    private fun startFilePicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            viewModel.loadFile(data?.data ?: return, activity?.contentResolver ?: return)
        } else {
            if (resultCode != Activity.RESULT_CANCELED) {
                Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
            }
        }
    }
}