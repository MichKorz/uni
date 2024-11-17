// boot.c

void wait(int time)
{
    volatile int timer = 0;
    while(timer < time)
    {
        timer++;
    }
}

void main() {
    char *video_memory = (char*) 0xB8000;
    char *message = "Hello, World!";

    unsigned char color = 0x00;
    int screen_width = 80;
    int screen_height = 25;
    int position = 0;

    for (int x = 0; x < 16*16; x++)
    {

        for (int i = 0; message[i] != '\0'; i++) {
            video_memory[position * 2] = message[i];
            video_memory[position * 2 + 1] = color;
            position++;
        }

        color++;
        position += (screen_width - position % screen_width);

        if (position >= screen_height * screen_width) position = 0;

        wait(1000000);
    }

    while(1);
}
