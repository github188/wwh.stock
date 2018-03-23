package cn.hzskt.bdtg.example;

import cn.hzskt.bdtg.common.action.BaseMagicAction;
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
