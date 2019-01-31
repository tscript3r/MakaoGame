package pl.tscript3r.makao.domain.player;

interface MoveOption {
    String getDescription();

    void execute();

    byte value();
}
