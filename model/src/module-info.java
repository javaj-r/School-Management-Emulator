/**
 * @author javid
 * Created on 12/25/2021
 */
module model {

    exports data.user to control;
    exports data.course to control;
    exports constant to control;
    exports model to control;

    requires util;
}