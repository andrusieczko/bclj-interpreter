# bclj-interpreter

A brainf*ck interpreter written in Clojure, just for fun :)

## Example:

```
+++++[>+++++[>+++>++++>++++>++++>++++<<<<<-]>>>+++>++>++
<<<<<<-]>>.>---.>-.>+.>--.[[-]<]<+++++[>++++++<-]>++.[-]
<+++++[>+++++[>+++>++++>++++>++++>++++>++++>++++>++++>++
++>+++++>++++>++++<<<<<<<<<<<<-]>-->++>>+++>+++>+++>+>>>
>+>++<<<<<<<<<<<<<-]>>.>.>.>-.>++.>.>.>+.>-.>---.>++.>+.
```

will print my name.

Why?

Read about the language [here](https://esolangs.org/wiki/brainfuck).

"Readable" version of the example above:

```
+++++
[
  >
  +++++
  [
    >+++
    >++++
    >++++
    >++++
    >++++
    <<<<
    <
    -
  ]
  >>>
  +++
  >++
  >++
  <<<<<
  <
  -
]
>>.
>---.
>-.
>+.
>--.

[[-]<]<

+++++
[
  >
  ++++++
  <
  -
]
>++.

[-]<

+++++
[
  >
  +++++
  [
    >+++
    >++++
    >++++
    >++++
    >++++
    >++++
    >++++
    >++++
    >++++
    >+++++
    >++++
    >++++
    <<<<<<<<<<<
    <
    -
  ]
  >
  --
  >++
  >>+++
  >+++
  >+++
  >+
  >>>>+
  >++
  <<<<<<<<<<<<
  <
  -
]
>>
.
>.
>.
>-.
>++.
>.
>.
>+.
>-.
>---.
>++.
>+.
```

## License

Copyright © 2016 Karol Andrusieczko

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
