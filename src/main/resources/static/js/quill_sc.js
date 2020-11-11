const quill = new Quill('#editor-container', {
  modules: {
    'toolbar': [
      [{'font': []}, {'size': []}],
      ['bold', 'italic', 'underline', 'strike'],
      [{'color': []}, {'background': []}],
      [{'script': 'super'}, {'script': 'sub'}],
      [{'header': '1'}, {'header': '2'}, 'blockquote', 'code-block'],
      [{'list': 'ordered'}, {'list': 'bullet'}, {'indent': '-1'}, {'indent': '+1'}],
      ['direction', {'align': []}],
      ['link', 'image', 'video', 'formula'],
      ['clean']
    ]
  },
  placeholder: 'อยากเขียนหยังกะเขียนโลด',
  theme: 'snow'
});

const form = document.querySelector('form');
form.onsubmit = function() {
  // Populate hidden form on submit
  const about = document.querySelector('input[name=content]');
  about.value = quill.root.innerHTML;

  // No back end to actually submit to!
};