package checkster.rules;

import checkster.CompatibilityRules;

public interface Rules {

    public static final CompatibilityRules SameServiceAndMajor = new Sequence(
            "Identificador de servicio y número mayor de versión coincidentes", new SameService(), new SameMajor());

}
