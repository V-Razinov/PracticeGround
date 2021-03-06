package ru.practiceground.presentation.filepicker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import ru.practiceground.R
import ru.practiceground.databinding.DialogCompressBinding
import ru.practiceground.databinding.FragmentFilePickerBinding
import ru.practiceground.other.extensions.string
import ru.practiceground.presentation.base.BaseFragment

class FilePickerFragment : BaseFragment() {

    override val viewModel: FilePickerViewModel by viewModels()

    private lateinit var binding: FragmentFilePickerBinding

    private val REQUEST_CODE: Int = this.id

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFilePickerBinding.inflate(inflater, container, false).apply {
            pickImageBtn.setOnClickListener { viewModel.onPickImageClick() }
            compressBtn.setOnClickListener { viewModel.onCompressClick() }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
    }

    private fun subscribe() {
        with(viewModel) {
            name.observe(binding.name::setText)
            size.observe(binding.size::setText)
            openFilePicker.setUnitObserver(::startFilePicker)
            fileUri.observe { uri ->
                Glide.with(this@FilePickerFragment).run {
                    if (uri == null) load(R.drawable.ic_round_image_24) else load(uri)
                }.into(binding.imageIv)
            }
            openPickSizeDialog.observe(::openSizeDialog)
        }
    }

    private fun openSizeDialog(size: Int) {
        var sizeTo = size
        val dialogBinding = DialogCompressBinding.inflate(layoutInflater).apply {
            val seekProgress = size.div(2)
            seekValue.text = seekProgress.string
            dialogSeekBar.apply {
                max = size
                setOnSeekBarChangeListener(object  : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(sB: SeekBar?, progress: Int, fromUser: Boolean) {
                        sizeTo = progress
                        seekValue.text = progress.string
                    }
                    override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
                    override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
                })
                progress = seekProgress
            }
        }
        AlertDialog.Builder(context ?: return)
            .setTitle("Выберите размер файла")
            .setMessage("Избражение будет постепенно сжиматьса, пока не будет меньше указанного размера или не сожмется до минимума")
            .setView(dialogBinding.root)
            .setPositiveButton("Сжать") { _, _ -> viewModel.onDialogOkClick(sizeTo * 1000) }
            .setNegativeButton("Отмена") { _, _ -> }
            .show()
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