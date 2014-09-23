#include <ruby.h>
#include "jellyfish.h"

VALUE jellyfish = Qnil;
VALUE c_jellyfish = Qnil;

VALUE method_soundex(int args, VALUE *argv, VALUE self) {
    const char *str;
    char *result;
    VALUE *rb_res;
    
    if (TYPE(*argv) == T_STRING) {
        str = RSTRING(*argv)->ptr;
        result = soundex(str);
        rb_res = rb_str_new2(result);
        free(result);
    } else {
        rb_raise(rb_eTypeError, "expects a string");
    }

    return rb_res;
}

void Init_jellyfish()
{
    jellyfish = rb_define_module("Jellyfish");
//    c_jellyfish = rb_define_class_under(jellyfish, "J", rb_cObject);
    rb_define_module_function(jellyfish, "soundex", method_soundex, -1);
}

