# yahtsee

This was implemented part of a group exercise at work. My goal was to experiment writing 'pure' code (no side effects) using the cats library.
I wanted to understand how much code readabilty was impacted. Well, unless the person reading the code is fairly familiar with monads/monad tranformers,
the code is probably quite indigest.

I tried various options to mix the State monad for the random generator and the IO monad to interact with the users, one of them based on cats 
monad transformers library (https://typelevel.org/blog/2018/10/06/intro-to-mtl.html).

Note: The typo in the name is here to avoid copyright infringement issues ;-)
