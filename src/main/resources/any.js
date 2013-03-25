$(document).ready(function() {
    $('#FORM_ID').submit(function(){
        var postData = $(this).serialize();
        var request = $('#prefix').val() + $(this).attr('name')

        // in case we have some path variables
        $.each($(this).serializeArray(), function(i, field) {
            request = request.replace('{' + field.name + '}',  field.value);
        });

        $.ajax({
            type: 'METHOD',
            data: postData,
            url: request,
            success: function(data){
                $('#FORM_ID_response').text(data).html();
            },
            error: function(xhr,err){
                $('#FORM_ID_response').text(xhr.statusText).html();
            }
        });
        return false;
    });
});
