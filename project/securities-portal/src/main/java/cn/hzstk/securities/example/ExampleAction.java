package cn.hzstk.securities.example;

import cn.hzstk.securities.common.action.BaseMagicAction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by allenwc on 16/4/6.
 */
@Controller
@RequestMapping("/example")
public class ExampleAction extends BaseMagicAction {

    @RequestMapping(value="hello")
    public String consumer() {
        return super.getNameSpace() + "hello";
    }

}
