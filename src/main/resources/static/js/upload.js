import { CKFinder } from './ckfinder.js';
let editor;
                  ClassicEditor
                    .create(
                      document.querySelector('#editor'),
                      {
                      plugins: [CKFinder, /* ... */ ],

                      // Enable the CKFinder button in the toolbar.
                      toolbar: ['ckfinder', /* ... */ ],

                      ckfinder: {
                        // Upload the images to the server using the CKFinder QuickUpload command.
                        uploadUrl: 'https://example.com/ckfinder/core/connector/php/connector.php?command=QuickUpload&type=Images&responseType=json',

                        // Define the CKFinder configuration (if necessary).
                        options: {
                          resourceType: 'Images'
                        }
                      }
                    }
                      // }
                    )
                    .then(newEditor => {
                      editor = newEditor;
                    })
                    .catch(error => {
                      console.error(error);
                    });