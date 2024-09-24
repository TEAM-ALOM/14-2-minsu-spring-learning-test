package cholog.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetterInjection {
    private InjectionBean injectionBean;
    @Autowired
    public void setInjectionBean(InjectionBean injectionBean) {
        this.injectionBean = injectionBean;
    }
/*
    Setter Injection으로 InjectionBean 주입받기
     */

    public String sayHello() {
        return injectionBean.hello();
    }
}
