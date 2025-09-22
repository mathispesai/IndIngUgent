% Corporate poster-template for KU Leuven en Kulak
% Stijn Rebry - februari 2014
% Tips en hints voor een beter resultaat altijd welkom
\NeedsTeXFormat{LaTeX2e}
\ProvidesClass{kulakposter2014}[2014/08/29 Poster class for KU Leuven]
\DeclareOption{photo}{
   \providecommand{\thephoto}{\usebox{\photobox}}
   \providecommand{\theemailaddress}{\usebox{\emailaddressbox}}
   }
\DeclareOption{nophoto}{
   \renewcommand{\thephoto}{}
   \renewcommand{\theemailaddress}{}
   }
\DeclareOption{kulak}{
   \providecommand{\Logo}{kulakLogo}
   }
\DeclareOption{kul}{
   \renewcommand{\Logo}{kuleuvenLogo}
   }
\DeclareOption*{\PassOptionsToClass{\CurrentOption}{sciposter}}
\ExecuteOptions{photo,portrait,kulak}
\ProcessOptions\relax
\LoadClass{sciposter}
\newlength{\eenheid}
\newcommand{\faculteit}{Wetenschappen}
\newcommand{\beeld}{WET}
\newcommand{\Copyright}{Rubik's Cube\copyright\ used by permission of Seven Towns Ltd. www.rubiks.com}
%%%2014 \setlength{\eenheid}{.012615643\paperheight}
\setlength{\eenheid}{.01\paperheight}
\setmargins[0\eenheid]
\addtolength{\topmargin}{12\eenheid} % 84 - 72 - 2 + 1
\addtolength{\oddsidemargin}{2\eenheid}
\addtolength{\textwidth}{-4\eenheid}
\addtolength{\textheight}{-13\eenheid} 
\RequirePackage{times}
\RequirePackage{titling}
\RequirePackage{multicol}
\RequirePackage{graphicx}
	\definecolor{kulblauw}{RGB}{0,64,122}
	\definecolor{kulappelblauwzeegroen}{RGB}{20,116,146}
	\definecolor{kuloranje}{RGB}{245,131,87}
	\definecolor{kulgroen}{RGB}{82,189,236}
	\definecolor{kulwetgroen}{rgb}{0.635,0.784,0.227}
	\definecolor{BoxCol}{rgb}{0.635,0.784,0.227}
	\definecolor{SectionCol}{rgb}{1,1,1}
\RequirePackage{tikz}
	\usetikzlibrary{arrows} 
	\usetikzlibrary{decorations.pathmorphing}
	\usetikzlibrary{backgrounds}
	\usetikzlibrary{fit}
	\usetikzlibrary{calc}
	\usetikzlibrary{shadows}
\newcommand{\photohere}{\begin{tabular}{@{}r@{}}
                	\thephoto\\
                	\theemailaddress  % % % your mail-address  % % % 
                \end{tabular}}
\RequirePackage{fancyhdr}
    \fancyhead{\begin{tikzpicture}[remember picture,overlay]
             \node at ($(current  page.south  west)  + (-\hoffset,  -\voffset)$)
         {
         \begin{tikzpicture}[remember picture, overlay,inner sep=0mm]
%%%2014             \fill[kulwetgroen] (0,0) rectangle (\paperwidth,74\eenheid);
%%%2014             \fill[white]  (\eenheid,\eenheid) rectangle ($ (\paperwidth,66\eenheid) + (-\eenheid,0) $); %($ (,) + (,) $)
             \fill[kulwetgroen] (0,0) rectangle (\paperwidth,95\eenheid);
             \fill[white]  (\eenheid,\eenheid) rectangle ($ (\paperwidth,88.8\eenheid) + (-\eenheid,0) $); %($ (,) + (,) $)
             \node[anchor=north west] (logo) at (2.5\eenheid,98\eenheid) {
                  \includegraphics[height=6.25\eenheid]{\Logo}};
             \node[anchor=east] (kubus) at ($ (\paperwidth,91.9\eenheid) + (-2\eenheid,0) $)   { 
                  \includegraphics[height=5\eenheid]{\beeld}};
                  \node[anchor=east,yshift=-.25em] at (kubus.south east) {\tiny \textcolor{white}{\Copyright}};
             \node[anchor= west] at (2.5\eenheid,90\eenheid) {\resizebox{!}{1.5\eenheid}{\textcolor{white}{\textbf{\faculteit}}}}; 
             \node[anchor=south east] at ($ (\paperwidth,2\eenheid) + (-2\eenheid,0) $) {%
             \photohere};
             \path (logo.east) -- (kubus.west) node [midway,inner sep=0pt] (a) {};
             \path (0,66\eenheid) -- (\paperwidth,66\eenheid) node(b) {};
             \path (a) -- (a |- b)node[at end] (c) {};
	      \end{tikzpicture}
	      };
	\end{tikzpicture}}
	\fancyfoot{}
	\setlength{\headheight}{0pt}
	\renewcommand{\headrulewidth}{0pt}
	\renewcommand{\footrulewidth}{0pt}
	\pagestyle{fancy}
\newcommand{\photo}[1]{\newsavebox{\photobox}\savebox{\photobox}{\includegraphics[width=5\eenheid]{#1}}}
\newcommand{\emailaddress}[1]{\newsavebox{\emailaddressbox}\savebox{\emailaddressbox}{\texttt{#1}}}
\AtBeginDocument{\thispagestyle{fancy}}
\renewcommand{\maketitle}{
\thispagestyle{fancy}
\begin{center}
\textcolor{kulwetgroen}{\huge\bf\thetitle}\\[.3\eenheid]
{\LARGE\theauthor}\\[.3\eenheid]
{\Large\theinstitute}\\[.5\eenheid]
\end{center}
}
\newcommand{\maketitlebar}{
	\fancyfoot{
\begin{tikzpicture}[remember picture,overlay]
\node[anchor=south,inner sep=0pt, node distance=0pt] (titeldink) at (c)
{ \resizebox{!}{4\eenheid}{
\begin{tabular}{@{}c@{}}
\textcolor{white}{\huge\bf\thetitle}\\[.3\eenheid]
{\textcolor{white}{\LARGE\theauthor}}\\[.3\eenheid]
{\textcolor{white}{\Large\theinstitute}}\\[.5\eenheid]
\end{tabular}}};
\end{tikzpicture}
}}