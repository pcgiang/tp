package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class RemarkTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Remark(null));
    }

    @Test
    void testEquals() {
        Remark remark = new Remark("A remark");
        Remark sameRemark = new Remark("A remark");
        Remark differentRemark = new Remark("A different remark");

        assertEquals(remark, sameRemark);
        assertEquals(remark, remark);
        assertNotEquals(remark, differentRemark);
    }
}
